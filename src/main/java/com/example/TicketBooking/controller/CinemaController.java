package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.CinemaDTO;
import com.example.TicketBooking.service.CinemaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cinemas")
@Tag(name = "Cinema", description = "Browse cinemas and manage them as an admin")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @GetMapping("")
    @Operation(summary = "List cinemas")
    public ResponseEntity<List<CinemaDTO>> getAllCinemas() {
        List<CinemaDTO> cinemaDTOS = cinemaService.getAllCinemas();
        return ResponseEntity.ok(cinemaDTOS);
    }

    @PostMapping("")
    @Operation(summary = "Create a cinema")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CinemaDTO> createCinema(@Valid @RequestBody CinemaDTO cinemaDTO) {
        CinemaDTO created = cinemaService.create(cinemaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a cinema")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CinemaDTO> updateCinema(@PathVariable Long id, @Valid @RequestBody CinemaDTO cinemaDTO) {
        CinemaDTO updated = cinemaService.update(id, cinemaDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a cinema")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long id) {
        cinemaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
