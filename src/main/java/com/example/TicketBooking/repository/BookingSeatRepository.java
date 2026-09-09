package com.example.TicketBooking.repository;

import com.example.TicketBooking.entity.BookingSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {
    boolean existsBySeat_Id(Long seatId);
}
