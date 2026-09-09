package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.GenreDTO;
import com.example.TicketBooking.entity.Genre;
import com.example.TicketBooking.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping("")
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        List<GenreDTO> genreDTOS = genreService.getAll();

        return ResponseEntity.ok(genreDTOS);
    }

    @PostMapping("")
    public ResponseEntity<GenreDTO> createGenre(@RequestBody GenreDTO genreDTO) {
        GenreDTO createdGenre = genreService.create(genreDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdGenre);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenreDTO> updateGenre(@PathVariable("id") Long id, @RequestBody GenreDTO genreDTO) {
        GenreDTO updatedGenre = genreService.update(id, genreDTO);

        return ResponseEntity.ok(updatedGenre);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable("id") Long id) {
        genreService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
