package com.example.TicketBooking.service;

import com.example.TicketBooking.dto.CinemaDTO;
import com.example.TicketBooking.entity.Cinema;
import com.example.TicketBooking.mapper.CinemaMapper;
import com.example.TicketBooking.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CinemaService {
    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;

    @Transactional(readOnly = true)
    public List<CinemaDTO> getAllCinemas() {
        List<Cinema> cinemas = cinemaRepository.findAll();
        return cinemaMapper.toDTOList(cinemas);
    }

    @Transactional
    public CinemaDTO create(CinemaDTO cinemaDTO) {
        Cinema saved = cinemaRepository.save(cinemaMapper.toEntity(cinemaDTO));
        return cinemaMapper.toDTO(saved);
    }

    @Transactional
    public CinemaDTO update(Long id, CinemaDTO cinemaDTO) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cinema not found"));
        cinemaMapper.updateEntityFromDTO(cinemaDTO, cinema);
        Cinema saved = cinemaRepository.save(cinema);
        return cinemaMapper.toDTO(cinema);
    }

    @Transactional
    public void delete(Long id) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cinema not found"));
        cinemaRepository.deleteById(id);
    }
}
