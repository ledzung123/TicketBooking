package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.SeatCreateDTO;
import com.example.TicketBooking.dto.SeatDTO;
import com.example.TicketBooking.service.SeatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
@Tag(name = "Seat", description = "Browse seats and manage them as an admin")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @GetMapping("")
    @Operation(summary = "List seats")
    public ResponseEntity<List<SeatDTO>> getAllSeats(@RequestParam(value = "hallId", required = false) Long hallId) {
        List<SeatDTO> seatDTOS = seatService.getAll(hallId);
        return ResponseEntity.ok(seatDTOS);
    }

    @PostMapping("")
    @Operation(summary = "Create a seat")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SeatDTO> createSeat(@Valid @RequestBody SeatCreateDTO seatCreateDTO) {
        SeatDTO seatDTO = seatService.create(seatCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(seatDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a seat")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SeatDTO> updateSeat(@PathVariable Long id
            , @Valid @RequestBody SeatCreateDTO seatCreateDTO) {
        SeatDTO seatDTO = seatService.update(id, seatCreateDTO);
        return ResponseEntity.ok(seatDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a seat")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        seatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
