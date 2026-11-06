package com.example.TicketBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest {
    @Schema(description = "Account email address", example = "alex@example.com")
    @Email
    @NotBlank(message = "Email must not be blank")
    private String email;

    @Schema(description = "Account password", example = "SecurePass123")
    @NotBlank(message = "Password must not be blank")
    private String password;
}
