package com.example.TicketBooking.config;

import com.example.TicketBooking.entity.Role;
import com.example.TicketBooking.entity.User;
import com.example.TicketBooking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@Slf4j
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment environment;

    @Override
    public void run(String... args) throws Exception {
        // A missing local password disables seeding without stopping startup.
        String adminPassword = environment.getProperty("app.admin.initial-password");
        if (adminPassword == null || adminPassword.isBlank()) {
            log.info("Admin seed skipped: no initial password configured.");
            return;
        }

        if (userRepository.existsByEmail("admin@moviebooking.com")) {
            log.info("Admin user already exists. Skipping seed.");
            return;
        }

        User user = new User();
        user.setEmail("admin@moviebooking.com");
        user.setPassword(passwordEncoder.encode(adminPassword));
        user.setFullName("Admin");
        user.setRole(Role.ADMIN);

        userRepository.save(user);

        log.warn("Admin user seeded successfully");
    }
}
