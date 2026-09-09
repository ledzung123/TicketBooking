package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.SeatCreateDTO;
import com.example.TicketBooking.dto.SeatDTO;
import com.example.TicketBooking.dto.ShowtimeCreateDTO;
import com.example.TicketBooking.dto.ShowtimeDTO;
import com.example.TicketBooking.entity.Showtime;
import com.example.TicketBooking.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {
    private final ShowtimeService showtimeService;

    @GetMapping("")
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
    public ResponseEntity<ShowtimeDTO> createShowtime(@RequestBody ShowtimeCreateDTO showtimeCreateDTO) {
        ShowtimeDTO showtimeDTO = showtimeService.create(showtimeCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(showtimeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowtimeDTO> updateSeat(@PathVariable Long id
            , @RequestBody ShowtimeCreateDTO seatCreateDTO) {
        ShowtimeDTO seatDTO = showtimeService.update(id, seatCreateDTO);
        return ResponseEntity.ok(seatDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        showtimeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
