package com.example.TicketBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    @Schema(description = "JWT access token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String accessToken;
    @Schema(description = "JWT refresh token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String refreshToken;
    @Schema(description = "Authorization scheme", example = "Bearer")
    private String tokenType;
    @Schema(description = "Access token lifetime in seconds", example = "900")
    private long expiresIn;
}
