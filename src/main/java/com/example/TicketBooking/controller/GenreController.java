package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.GenreDTO;
import com.example.TicketBooking.entity.Genre;
import com.example.TicketBooking.service.GenreService;
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
@RequestMapping("/api/v1/genres")
@Tag(name = "Genre", description = "Browse genres and manage them as an admin")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("")
    @Operation(summary = "List genres")
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        List<GenreDTO> genreDTOS = genreService.getAll();

        return ResponseEntity.ok(genreDTOS);
    }

    @PostMapping("")
    @Operation(summary = "Create a genre")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenreDTO> createGenre(@Valid @RequestBody GenreDTO genreDTO) {
        GenreDTO createdGenre = genreService.create(genreDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdGenre);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a genre")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GenreDTO> updateGenre(@PathVariable("id") Long id, @Valid @RequestBody GenreDTO genreDTO) {
        GenreDTO updatedGenre = genreService.update(id, genreDTO);

        return ResponseEntity.ok(updatedGenre);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a genre")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteGenre(@PathVariable("id") Long id) {
        genreService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
