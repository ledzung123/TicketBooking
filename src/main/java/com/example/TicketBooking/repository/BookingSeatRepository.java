package com.example.TicketBooking.repository;

import com.example.TicketBooking.entity.BookingSeat;
import com.example.TicketBooking.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {
    boolean existsBySeat_Id(Long seatId);
    boolean existsByShowtimeIdAndSeat_IdAndBooking_StatusNot(Long showtimeId, Long seatId, Status status);
}
