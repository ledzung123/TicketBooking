package com.example.TicketBooking.repository;

import com.example.TicketBooking.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
}
