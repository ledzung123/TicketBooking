package com.example.TicketBooking.security;

import com.example.TicketBooking.entity.User;
import com.example.TicketBooking.exception.ResourceNotFoundException;
import com.example.TicketBooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SecurityService {
    private final UserRepository userRepository;

    public boolean isOwner(Long resourceUserId, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));

        return user.getId().equals(resourceUserId);
    }

    public boolean isAdmin(Authentication authentication) {
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
    }
}
