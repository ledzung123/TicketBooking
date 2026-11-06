package com.example.TicketBooking.security;

import com.example.TicketBooking.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Getter
    @Value("${jwt.access-expiration-ms}")
    private long accessExpirationMs;

    @Getter
    @Value("${jwt.refresh-expiration-ms}")
    private long refreshExpirationMs;

    public SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(User user) {
        Date now = new Date();

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name()) // put role
                .issuedAt(new Date()) // set published time
                .expiration(new Date(now.getTime() + accessExpirationMs))
                .signWith(getSigningKey(), Jwts.SIG.HS256) // sign for token using secret key and HS256
                .compact(); // pack every thing into string
    }

    public String generateRefreshToken(User user) {
        Date now = new Date();

        return Jwts.builder() // build new JWT
                .subject(user.getEmail()) // put user's email into subject field(authorize)
                .id(UUID.randomUUID().toString()) // generate random uuid string to put into jti field(db)
                .issuedAt(new Date())
                .expiration(new Date(now.getTime() + refreshExpirationMs))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        // 1. Parse token, use key to verify signature
        Claims payload = Jwts.parser()
                .verifyWith(getSigningKey()) // use signing key to verify whether token is fake or not
                .build()
                .parseSignedClaims(token) // Parse token (if signature is wrong or outdated will throw exception
                .getPayload();
        return payload.getSubject();
    }

    public String extractJti(String token) {
        // 1. Parse token, use key to verify signature
        Claims payload = Jwts.parser()
                .verifyWith(getSigningKey()) // use signing key to verify whether token is fake or not
                .build()
                .parseSignedClaims(token) // Parse token (if signature is wrong or outdated will throw exception
                .getPayload();
        return payload.getId();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey()) // use signing key to verify whether token is fake or not
                    .build()
                    .parseSignedClaims(token);

            return true;
        } catch (JwtException e) {
            // Wrong token, outdated token, wrong format
            log.warn("Invalid JWT token: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            // null token, empty token
            return false;
        }

    }

}
