package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.HallCreateDTO;
import com.example.TicketBooking.dto.HallDTO;
import com.example.TicketBooking.entity.Hall;
import com.example.TicketBooking.service.HallService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/halls")
@Tag(name = "Hall", description = "Browse screening halls and manage them as an admin")
@RequiredArgsConstructor
public class HallController {
    private final HallService hallService;

    @GetMapping("")
    @Operation(summary = "List halls")
    public ResponseEntity<List<HallDTO>> getAllHallByCinemaId(
            @RequestParam(value = "cinemaId", required = false) Long cinemaId) {
        List<HallDTO> hallDTOS = hallService.getAll(cinemaId);
        return ResponseEntity.ok(hallDTOS);
    }

    @PostMapping("")
    @Operation(summary = "Create a hall")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HallDTO> createHall(@Valid @RequestBody HallCreateDTO hallCreateDTO) {
        HallDTO hallDTO = hallService.create(hallCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(hallDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a hall")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HallDTO> updateHall(@PathVariable Long id,
                                              @Valid @RequestBody HallCreateDTO hallCreateDTO) {
        HallDTO hallDTO = hallService.update(id, hallCreateDTO);
        return ResponseEntity.ok(hallDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a hall")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteHall(@PathVariable Long id) {
        hallService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
