package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.SeatCreateDTO;
import com.example.TicketBooking.dto.SeatDTO;
import com.example.TicketBooking.dto.ShowtimeCreateDTO;
import com.example.TicketBooking.dto.ShowtimeDTO;
import com.example.TicketBooking.entity.Showtime;
import com.example.TicketBooking.service.ShowtimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/showtimes")
@Tag(name = "Showtime", description = "Browse upcoming showtimes and manage them as an admin")
@RequiredArgsConstructor
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    @GetMapping("")
    @Operation(summary = "List upcoming showtimes")
    public ResponseEntity<?> getUpcoming(
            @RequestParam(value = "movieId", required = false) Long movieId,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        if (movieId != null) {
            List<ShowtimeDTO> showtimeDTOS = showtimeService.getUpcomingByMovie(movieId);
            return ResponseEntity.ok(showtimeDTOS);
        }
        Page<ShowtimeDTO> showtimeDTOS = showtimeService.getUpcoming(pageable);
        return ResponseEntity.ok(showtimeDTOS);
    }


    @PostMapping("")
    @Operation(summary = "Create a showtime")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ShowtimeDTO> createShowtime(@Valid @RequestBody ShowtimeCreateDTO showtimeCreateDTO) {
        ShowtimeDTO showtimeDTO = showtimeService.create(showtimeCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(showtimeDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a showtime")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ShowtimeDTO> updateSeat(@PathVariable Long id
            , @Valid @RequestBody ShowtimeCreateDTO seatCreateDTO) {
        ShowtimeDTO seatDTO = showtimeService.update(id, seatCreateDTO);
        return ResponseEntity.ok(seatDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a showtime")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        showtimeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
