package com.example.TicketBooking.service;

import com.example.TicketBooking.dto.AuthResponse;
import com.example.TicketBooking.dto.LoginRequest;
import com.example.TicketBooking.dto.RefreshTokenRequest;
import com.example.TicketBooking.dto.RegisterRequest;
import com.example.TicketBooking.entity.RefreshToken;
import com.example.TicketBooking.entity.Role;
import com.example.TicketBooking.entity.User;
import com.example.TicketBooking.exception.DuplicateResourceException;
import com.example.TicketBooking.exception.ResourceNotFoundException;
import com.example.TicketBooking.repository.RefreshTokenRepository;
import com.example.TicketBooking.repository.SeatRepository;
import com.example.TicketBooking.repository.UserRepository;
import com.example.TicketBooking.security.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        user.setPhone(registerRequest.getPhone());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.CUSTOMER);

        userRepository.save(user);

        return generateTokens(user);
    }

    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = new
                UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        try {
            authenticationManager.authenticate(token);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Wrong email or password");
        }

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );

        return generateTokens(user);
    }

    private AuthResponse generateTokens(User user) {
        String accessToken = jwtTokenProvider.generateAccessToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        RefreshToken token = new RefreshToken();
        token.setId(jwtTokenProvider.extractJti(refreshToken));
        token.setToken(refreshToken);
        token.setUser(user);
        token.setExpiryDate(Instant.now().plusMillis(jwtTokenProvider.getRefreshExpirationMs()));

        refreshTokenRepository.save(token);

        return new AuthResponse(accessToken, refreshToken
                , "Bearer", jwtTokenProvider.getAccessExpirationMs()/1000);
    }

    @Transactional
    public AuthResponse refresh(RefreshTokenRequest request) {
        String email;
        String jti;

        try {
            email = jwtTokenProvider.extractEmail(request.getRefreshToken());
            jti = jwtTokenProvider.extractJti(request.getRefreshToken());
        } catch(ExpiredJwtException ex) {
            throw new BadCredentialsException("Token is outdated");
        } catch (JwtException ex) {
            throw new BadCredentialsException("Invalid token");
        }

        RefreshToken refreshToken = refreshTokenRepository.findById(jti)
                .orElseThrow(() -> new BadCredentialsException("Token has been recalled"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new BadCredentialsException("Token is outdated");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User does not exists"));

        refreshTokenRepository.delete(refreshToken);

        String newAccessToken = jwtTokenProvider.generateAccessToken(user);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);

        RefreshToken token = new RefreshToken();
        token.setId(jwtTokenProvider.extractJti(newRefreshToken));
        token.setToken(newRefreshToken);
        token.setUser(user);
        token.setExpiryDate(Instant.now().plusMillis(jwtTokenProvider.getRefreshExpirationMs()));

        refreshTokenRepository.save(token);

        return new AuthResponse(newAccessToken, newRefreshToken
                , "Bearer", jwtTokenProvider.getAccessExpirationMs()/1000);
    }

    @Transactional
    public void logout(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));

        refreshTokenRepository.deleteByUserId(user.getId());
    }
}
