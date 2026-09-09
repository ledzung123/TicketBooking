package com.example.TicketBooking.repository;

import com.example.TicketBooking.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HallRepository extends JpaRepository<Hall, Long> {
    List<Hall> findByCinema_Id(Long cinemaId);
}
