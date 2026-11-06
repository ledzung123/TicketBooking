package com.example.TicketBooking.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    public void doFilterInternal(HttpServletRequest request
            , HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        String email;

        try {
            // call extractEmail: jjwt library automatically verify and check exp
            email = jwtTokenProvider.extractEmail(token);

        } catch(ExpiredJwtException ex) {
            authenticationEntryPoint.commence(request, response, new BadCredentialsException("Token is outdated"));
            return;
        } catch (JwtException ex) {
            authenticationEntryPoint.commence(request, response, new BadCredentialsException("Invalid token"));
            return;
        }


        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authenticationToken = new
                    UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("api/v1/auth/login")
                || path.equals("api/v1/auth/register")
                || path.equals("api/v1/auth/refresh");
    }
}
