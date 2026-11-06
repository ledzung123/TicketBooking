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
public class RegisterRequest {
    @Schema(description = "Account email address", example = "alex@example.com")
    @Email
    @NotBlank(message = "Email must not be blank")
    private String email;

    @Schema(description = "Password with at least eight characters", example = "SecurePass123")
    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be over 8 characters")
    private String password;

    @Schema(description = "User's full name", example = "Alex Nguyen")
    @NotBlank(message = "Full name must not be blank")
    private String fullName;

    @Schema(description = "Ten-digit phone number", example = "0912345678")
    @Size(min = 10, max = 10, message = "Phone number must have 10 characters")
    private String phone;
}
