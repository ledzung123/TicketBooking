package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.AuthResponse;
import com.example.TicketBooking.dto.LoginRequest;
import com.example.TicketBooking.dto.RefreshTokenRequest;
import com.example.TicketBooking.dto.RegisterRequest;
import com.example.TicketBooking.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Register, sign in, refresh tokens, and sign out")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a user")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        AuthResponse authResponse = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    @Operation(summary = "Sign in")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.login(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh an access token")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        AuthResponse authResponse = authService.refresh(request);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    @Operation(summary = "Sign out")
    public ResponseEntity<Void> logout(Authentication authentication) {
        authService.logout(authentication);
        return ResponseEntity.noContent().build();
    }
}
