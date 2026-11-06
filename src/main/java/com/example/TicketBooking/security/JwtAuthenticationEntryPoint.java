package com.example.TicketBooking.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;


import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response
            , AuthenticationException authException) throws IOException, ServletException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED
                , authException.getMessage());

        problemDetail.setProperty("errorCode", "UNAUTHORIZED");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);

        response.getWriter().write(objectMapper.writeValueAsString(problemDetail));
    }

}
