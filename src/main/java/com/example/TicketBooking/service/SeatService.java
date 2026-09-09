package com.example.TicketBooking.service;

import com.example.TicketBooking.dto.SeatCreateDTO;
import com.example.TicketBooking.dto.SeatDTO;
import com.example.TicketBooking.entity.Hall;
import com.example.TicketBooking.entity.Seat;
import com.example.TicketBooking.mapper.SeatMapper;
import com.example.TicketBooking.repository.BookingSeatRepository;
import com.example.TicketBooking.repository.HallRepository;
import com.example.TicketBooking.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final HallRepository hallRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final SeatMapper seatMapper;

    @Transactional(readOnly = true)
    public List<SeatDTO> getAll(Long hallId) {
        if (hallId != null) {
            return seatMapper.toDTOList(seatRepository.findByHall_Id(hallId));
        }

        return seatMapper.toDTOList(seatRepository.findAll());
    }

    @Transactional
    public SeatDTO create(SeatCreateDTO seatCreateDTO) {
        if (seatRepository.existsByHall_IdAndSeatCode(seatCreateDTO.getHallId(), seatCreateDTO.getSeatCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seat already exists");
        }

        Hall hall = hallRepository.findById(seatCreateDTO.getHallId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hall not found"));

        Seat seat = seatMapper.toEntity(seatCreateDTO);
        seat.setHall(hall);

        Seat saved = seatRepository.save(seat);
        return seatMapper.toDTO(saved);
    }

    @Transactional
    public SeatDTO update(Long id, SeatCreateDTO seatCreateDTO) {
        Hall hall = hallRepository.findById(seatCreateDTO.getHallId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hall not found"));

        Seat seat = seatRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat not fount"));

        seatMapper.updateEntityFromDTO(seatCreateDTO, seat);
        seat.setHall(hall);

        Seat saved = seatRepository.save(seat);
        return seatMapper.toDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (bookingSeatRepository.existsBySeat_Id(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Seat still exists in booking seats");
        }

        Seat seat = seatRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seat not found"));

        seatRepository.deleteById(id);
    }
}
