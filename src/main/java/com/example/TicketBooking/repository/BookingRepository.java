package com.example.TicketBooking.repository;

import com.example.TicketBooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT DISTINCT b FROM Booking b JOIN FETCH b.bookingSeats")
    List<Booking> findAllWithBookingSeats();

    @Query("SELECT DISTINCT b FROM Booking b JOIN FETCH b.bookingSeats WHERE b.id = :id")
    Optional<Booking> findByIdWithBookingSeats(@Param("id") Long id);
}
