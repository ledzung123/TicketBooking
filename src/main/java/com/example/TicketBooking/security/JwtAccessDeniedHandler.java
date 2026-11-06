package com.example.TicketBooking.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    public final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response
            , AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN
                , "You can not access these materials");

        problemDetail.setProperty("errorCode", "FORBIDDEN");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(403);

        response.getWriter().write(objectMapper.writeValueAsString(problemDetail));
    }
}
