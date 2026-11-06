package com.example.TicketBooking.repository;

import com.example.TicketBooking.entity.RefreshToken;
import org.hibernate.boot.models.JpaAnnotations;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserId(Long userId);
    boolean existsByUserId(Long userId);
}
