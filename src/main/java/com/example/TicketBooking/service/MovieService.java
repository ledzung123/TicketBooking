    package com.example.TicketBooking.service;

    import com.example.TicketBooking.dto.MovieCreateDTO;
    import com.example.TicketBooking.dto.MovieResponseDTO;
    import com.example.TicketBooking.dto.MovieSummaryDTO;
    import com.example.TicketBooking.entity.Genre;
    import com.example.TicketBooking.entity.Movie;
    import com.example.TicketBooking.exception.ResourceInUseException;
    import com.example.TicketBooking.exception.ResourceNotFoundException;
    import com.example.TicketBooking.mapper.MovieMapper;
    import com.example.TicketBooking.repository.GenreRepository;
    import com.example.TicketBooking.repository.MovieRepository;
    import com.example.TicketBooking.repository.ShowtimeRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.server.ResponseStatusException;


    import java.util.List;
    import java.util.Optional;
    import java.util.Set;
    import java.util.stream.Collectors;

    @Service
    @RequiredArgsConstructor
    public class MovieService {
        private final MovieRepository movieRepository;
        private final GenreRepository genreRepository;
        private final ShowtimeRepository showtimeRepository;
        private final MovieMapper movieMapper;

        public List<Movie> getAllMoviesWithGenres() {
            return movieRepository.findAllMoviesWithGenres();
        }

        public Optional<Movie> getMovieByIdWithGenres(Long movieId) {
            return movieRepository.findMovieByIdWithGenres(movieId);
        }

        @Transactional(readOnly = true)
        public Page<MovieSummaryDTO> getMoviesPaged(Pageable pageable) {
            return movieRepository.findAll(pageable).map(movie -> movieMapper.toSummaryDTO(movie));
        }

        @Transactional(readOnly = true)
        public MovieResponseDTO getMovieDetail(Long id) {
            Movie movie = movieRepository.findMovieByIdWithGenres(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

            return movieMapper.toResponseDTO(movie);
        }

        @Transactional(readOnly = true)
        public List<MovieSummaryDTO> searchByTitle(String keyword) {
            return movieRepository.findByTitleContainingIgnoreCase(keyword)
                    .stream().map(movie -> movieMapper.toSummaryDTO(movie))
                    .collect(Collectors.toList());
        }

        @Transactional
        public MovieResponseDTO create(MovieCreateDTO movieCreateDTO) {
            Set<Genre> genres = genreRepository.findAllById(movieCreateDTO.getGenreIds())
                    .stream().collect(Collectors.toSet());
            Movie movie = movieMapper.toEntity(movieCreateDTO);
            movie.setGenres(genres);
            Movie saved = movieRepository.save(movie);
            return movieMapper.toResponseDTO(saved);
        }

        @Transactional
        public MovieResponseDTO update(Long id, MovieCreateDTO movieCreateDTO) {
            Set<Genre> genres = genreRepository.findAllById(movieCreateDTO.getGenreIds())
                    .stream().collect(Collectors.toSet());
            Movie movie = movieRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

            movie.setGenres(genres);
            movieMapper.updateEntityFromDTO(movieCreateDTO, movie);
            Movie saved = movieRepository.save(movie);
            return movieMapper.toResponseDTO(saved);
        }

        @Transactional
        public void delete(Long id) {
            if (showtimeRepository.existsByMovie_Id(id)) {
                throw new ResourceInUseException("Movies still exists in showtime");
            }

            Movie movie = movieRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

            movieRepository.deleteById(id);
        }
    }
