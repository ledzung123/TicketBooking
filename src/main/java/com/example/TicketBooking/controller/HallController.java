package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.HallCreateDTO;
import com.example.TicketBooking.dto.HallDTO;
import com.example.TicketBooking.entity.Hall;
import com.example.TicketBooking.service.HallService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/halls")
@RequiredArgsConstructor
public class HallController {
    private final HallService hallService;

    @GetMapping("")
    public ResponseEntity<List<HallDTO>> getAllHallByCinemaId(
            @RequestParam(value = "cinemaId", required = false) Long cinemaId) {
        List<HallDTO> hallDTOS = hallService.getAll(cinemaId);
        return ResponseEntity.ok(hallDTOS);
    }

    @PostMapping("")
    public ResponseEntity<HallDTO> createHall(@Valid @RequestBody HallCreateDTO hallCreateDTO) {
        HallDTO hallDTO = hallService.create(hallCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(hallDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HallDTO> updateHall(@PathVariable Long id,
                                              @Valid @RequestBody HallCreateDTO hallCreateDTO) {
        HallDTO hallDTO = hallService.update(id, hallCreateDTO);
        return ResponseEntity.ok(hallDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHall(@PathVariable Long id) {
        hallService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
