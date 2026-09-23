package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.SeatCreateDTO;
import com.example.TicketBooking.dto.SeatDTO;
import com.example.TicketBooking.service.SeatService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @GetMapping("")
    public ResponseEntity<List<SeatDTO>> getAllSeats(@RequestParam(value = "hallId", required = false) Long hallId) {
        List<SeatDTO> seatDTOS = seatService.getAll(hallId);
        return ResponseEntity.ok(seatDTOS);
    }

    @PostMapping("")
    public ResponseEntity<SeatDTO> createSeat(@Valid @RequestBody SeatCreateDTO seatCreateDTO) {
        SeatDTO seatDTO = seatService.create(seatCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(seatDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatDTO> updateSeat(@PathVariable Long id
            , @Valid @RequestBody SeatCreateDTO seatCreateDTO) {
        SeatDTO seatDTO = seatService.update(id, seatCreateDTO);
        return ResponseEntity.ok(seatDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        seatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
