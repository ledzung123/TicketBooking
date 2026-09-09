package com.example.TicketBooking.repository;

import com.example.TicketBooking.dto.MovieResponseDTO;
import com.example.TicketBooking.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT DISTINCT m from Movie m LEFT JOIN FETCH m.genres")
    List<Movie> findAllMoviesWithGenres();

    @Query("SELECT DISTINCT m FROM Movie m LEFT JOIN FETCH m.genres WHERE m.id = :movieId")
    Optional<Movie> findMovieByIdWithGenres(@Param("movieId") Long movieId);

    List<Movie> findByTitleContainingIgnoreCase(String keyword);

    boolean existsByGenres_Id(Long genreId);


}
