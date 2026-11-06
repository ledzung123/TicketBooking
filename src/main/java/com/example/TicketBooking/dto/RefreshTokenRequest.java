package com.example.TicketBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenRequest {
    @Schema(description = "Refresh token returned at sign-in", example = "eyJhbGciOiJIUzI1NiJ9...")
    @NotBlank(message = "Refresh token must not be blank")
    private String refreshToken;
}
