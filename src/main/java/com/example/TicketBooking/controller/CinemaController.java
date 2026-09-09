package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.CinemaDTO;
import com.example.TicketBooking.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cinemas")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @GetMapping("")
    public ResponseEntity<List<CinemaDTO>> getAllCinemas() {
        List<CinemaDTO> cinemaDTOS = cinemaService.getAllCinemas();
        return ResponseEntity.ok(cinemaDTOS);
    }

    @PostMapping("")
    public ResponseEntity<CinemaDTO> createCinema(@RequestBody CinemaDTO cinemaDTO) {
        CinemaDTO created = cinemaService.create(cinemaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CinemaDTO> updateCinema(@PathVariable Long id, @RequestBody CinemaDTO cinemaDTO) {
        CinemaDTO updated = cinemaService.update(id, cinemaDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long id) {
        cinemaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
