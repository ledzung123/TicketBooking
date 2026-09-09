package com.example.TicketBooking.service;

import com.example.TicketBooking.dto.HallCreateDTO;
import com.example.TicketBooking.dto.HallDTO;
import com.example.TicketBooking.entity.Cinema;
import com.example.TicketBooking.entity.Hall;
import com.example.TicketBooking.mapper.HallMapper;
import com.example.TicketBooking.repository.CinemaRepository;
import com.example.TicketBooking.repository.HallRepository;
import com.example.TicketBooking.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HallService {
    private final HallRepository hallRepository;
    private final CinemaRepository cinemaRepository;
    private final ShowtimeRepository showtimeRepository;
    private final HallMapper hallMapper;

    @Transactional(readOnly = true)
    public List<HallDTO> getAll(Long cinemaId) {
        if (cinemaId != null) {
            return hallMapper.toDTOList(hallRepository.findByCinema_Id(cinemaId));
        }

        return hallMapper.toDTOList(hallRepository.findAll());
    }

    @Transactional
    public HallDTO create(HallCreateDTO hallCreateDTO) {
        Cinema cinema = cinemaRepository.findById(hallCreateDTO.getCinemaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cinema not found"));

        Hall hall = hallMapper.toEntity(hallCreateDTO);
        hall.setCinema(cinema);
        Hall saved = hallRepository.save(hall);

        return hallMapper.toDTO(saved);
    }

    @Transactional
    public HallDTO update(Long id, HallCreateDTO hallCreateDTO) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hall not found"));

        Cinema cinema = cinemaRepository.findById(hallCreateDTO.getCinemaId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cinema not found"));

        hallMapper.updateEntityFromDTO(hallCreateDTO, hall);
        hall.setCinema(cinema);
        Hall saved = hallRepository.save(hall);

        return hallMapper.toDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (showtimeRepository.existsByHall_Id(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Hall is still used by showtime");
        }

        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hall not found"));

        hallRepository.deleteById(id);
    }
}
