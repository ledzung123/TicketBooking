package com.example.TicketBooking.repository;

import com.example.TicketBooking.dto.ShowtimeDTO;
import com.example.TicketBooking.entity.Showtime;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {
    @Query("SELECT s FROM Showtime s JOIN FETCH s.movie JOIN FETCH s.hall")
    List<Showtime> findAllWithMovieAndHall();

    @Query("SELECT s FROM Showtime s JOIN FETCH s.movie JOIN FETCH s.hall WHERE s.id = :showtimeId")
    Optional<Showtime> findByIdWithMovieAndHall(@Param("showtimeId") Long showtime_id);

    boolean existsByHall_Id(Long hallId);

    boolean existsByMovie_Id(Long movieId);

    List<Showtime> findByMovie_idAndStartTimeAfter(Long movieId, LocalDateTime from);

    @Query("SELECT s FROM Showtime s JOIN FETCH s.movie JOIN FETCH s.hall WHERE s.startTime > :from")
    Page<Showtime> findUpComingWithMovieAndHall(@Param("from") LocalDateTime from, Pageable pageable);
}
