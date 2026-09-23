package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.MovieCreateDTO;
import com.example.TicketBooking.dto.MovieResponseDTO;
import com.example.TicketBooking.dto.MovieSummaryDTO;
import com.example.TicketBooking.service.MovieService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@Validated
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("")
    public ResponseEntity<Page<MovieSummaryDTO>> getPagedMovies(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<MovieSummaryDTO> movieSummaryDTO = movieService.getMoviesPaged(pageable);
        return ResponseEntity.ok(movieSummaryDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> getDetailedMovie(@PathVariable Long id) {
        MovieResponseDTO movieResponseDTO = movieService.getMovieDetail(id);
        return ResponseEntity.ok(movieResponseDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MovieSummaryDTO>> searchByTitle(
            @RequestParam(value = "keyword") @NotBlank String keyword) {
        List<MovieSummaryDTO> movieSummaryDTOS = movieService.searchByTitle(keyword);
        return ResponseEntity.ok(movieSummaryDTOS);
    }

    @PostMapping("")
    public ResponseEntity<MovieResponseDTO> createMovie(@Valid @RequestBody MovieCreateDTO movieCreateDTO) {
        MovieResponseDTO movieResponseDTO = movieService.create(movieCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponseDTO> updateMovie(@PathVariable Long id
            , @Valid @RequestBody MovieCreateDTO movieCreateDTO) {
        MovieResponseDTO movieResponseDTO = movieService.update(id, movieCreateDTO);
        return ResponseEntity.ok(movieResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
