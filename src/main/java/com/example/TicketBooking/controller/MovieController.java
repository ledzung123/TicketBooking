package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.MovieCreateDTO;
import com.example.TicketBooking.dto.MovieResponseDTO;
import com.example.TicketBooking.dto.MovieSummaryDTO;
import com.example.TicketBooking.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@Tag(name = "Movie", description = "Browse and search movies; admins can manage them")
@Validated
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping("")
    @Operation(summary = "List movies")
    public ResponseEntity<Page<MovieSummaryDTO>> getPagedMovies(
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<MovieSummaryDTO> movieSummaryDTO = movieService.getMoviesPaged(pageable);
        return ResponseEntity.ok(movieSummaryDTO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie details")
    public ResponseEntity<MovieResponseDTO> getDetailedMovie(@PathVariable Long id) {
        MovieResponseDTO movieResponseDTO = movieService.getMovieDetail(id);
        return ResponseEntity.ok(movieResponseDTO);
    }

    @GetMapping("/search")
    @Operation(summary = "Search movies by title")
    public ResponseEntity<List<MovieSummaryDTO>> searchByTitle(
            @RequestParam(value = "keyword") @NotBlank String keyword) {
        List<MovieSummaryDTO> movieSummaryDTOS = movieService.searchByTitle(keyword);
        return ResponseEntity.ok(movieSummaryDTOS);
    }

    @PostMapping("")
    @Operation(summary = "Create a movie")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponseDTO> createMovie(@Valid @RequestBody MovieCreateDTO movieCreateDTO) {
        MovieResponseDTO movieResponseDTO = movieService.create(movieCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(movieResponseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a movie")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MovieResponseDTO> updateMovie(@PathVariable Long id
            , @Valid @RequestBody MovieCreateDTO movieCreateDTO) {
        MovieResponseDTO movieResponseDTO = movieService.update(id, movieCreateDTO);
        return ResponseEntity.ok(movieResponseDTO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a movie")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
