package com.example.TicketBooking.repository;

import com.example.TicketBooking.entity.Booking;
import com.example.TicketBooking.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT DISTINCT b FROM Booking b" +
            "    JOIN FETCH b.bookingSeats" +
            "    JOIN FETCH b.showtime st" +
            "    JOIN FETCH st.movie")
    List<Booking> findAllWithBookingSeats();

    @Query("""
        SELECT DISTINCT b FROM Booking b
        JOIN FETCH b.bookingSeats bs
        JOIN FETCH bs.seat
        JOIN FETCH b.showtime st
        JOIN FETCH st.movie
        JOIN FETCH st.hall h
        JOIN FETCH h.cinema
        WHERE b.id = :id
    """)
    Optional<Booking> findByIdWithBookingSeats(@Param("id") Long id);

    @Query("""
        SELECT DISTINCT b FROM Booking b
        JOIN FETCH b.bookingSeats
        JOIN FETCH b.showtime st
        JOIN FETCH st.movie
        WHERE b.user.id = :userId
        ORDER BY b.createdAt DESC
    """)
    List<Booking> findByUser_IdOrderByBookingTimeDesc(Long userId);

    Page<Booking> findByStatus(Status status, Pageable pageable);
}
