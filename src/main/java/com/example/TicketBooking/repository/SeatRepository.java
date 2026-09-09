package com.example.TicketBooking.repository;

import com.example.TicketBooking.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByHall_Id(Long hallId);
    boolean existsByHall_IdAndSeatCode(Long hallId, String seatCode);
}
