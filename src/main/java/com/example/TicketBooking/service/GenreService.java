package com.example.TicketBooking.service;

import com.example.TicketBooking.dto.GenreDTO;
import com.example.TicketBooking.entity.Genre;
import com.example.TicketBooking.exception.DuplicateResourceException;
import com.example.TicketBooking.exception.ResourceInUseException;
import com.example.TicketBooking.exception.ResourceNotFoundException;
import com.example.TicketBooking.mapper.GenreMapper;
import com.example.TicketBooking.repository.GenreRepository;
import com.example.TicketBooking.repository.MovieRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final GenreMapper genreMapper;

    @Transactional(readOnly = true)
    public List<GenreDTO> getAll() {
        List<Genre> genres = genreRepository.findAll();
        return genreMapper.toDTOList(genres);
    }

    @Transactional
    public GenreDTO create(GenreDTO genreDTO) {
        if (genreRepository.existsByName(genreDTO.getName())) {
            throw new DuplicateResourceException("Genre name already exists");
        }

        Genre saved = genreRepository.save(genreMapper.toEntity(genreDTO));
        return genreMapper.toDTO(saved);
    }

    @Transactional
    public GenreDTO update(Long id, GenreDTO genreDTO) {
        Genre genre = genreRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Genre not found"));

        genre.setName(genreDTO.getName());
        Genre saved = genreRepository.save(genre);

        return genreMapper.toDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (movieRepository.existsByGenres_Id(id)) {
            throw new ResourceInUseException("Genre still used by some movies");
        }

        Genre genre = genreRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Genre not found"));

        genreRepository.deleteById(id);
    }
}
