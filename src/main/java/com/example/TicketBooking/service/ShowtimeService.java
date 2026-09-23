    package com.example.TicketBooking.service;

    import com.example.TicketBooking.dto.ShowtimeCreateDTO;
    import com.example.TicketBooking.dto.ShowtimeDTO;
    import com.example.TicketBooking.entity.Hall;
    import com.example.TicketBooking.entity.Movie;
    import com.example.TicketBooking.entity.Showtime;
    import com.example.TicketBooking.exception.ResourceNotFoundException;
    import com.example.TicketBooking.mapper.ShowtimeMapper;
    import com.example.TicketBooking.repository.HallRepository;
    import com.example.TicketBooking.repository.MovieRepository;
    import com.example.TicketBooking.repository.ShowtimeRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.http.HttpStatus;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;
    import org.springframework.web.server.ResponseStatusException;

    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.Optional;

    @Service
    @RequiredArgsConstructor
    public class ShowtimeService {
        private final ShowtimeRepository showtimeRepository;
        private final MovieRepository movieRepository;
        private final HallRepository hallRepository;
        private final ShowtimeMapper showtimeMapper;

        public List<Showtime> getAllShowtimeWithMovieAndHall() {
            return showtimeRepository.findAllWithMovieAndHall();
        }

        public Optional<Showtime> getShowtimeByIdWithMovieAndHall(Long showtimeId) {
            return showtimeRepository.findByIdWithMovieAndHall(showtimeId);
        }

        @Transactional(readOnly = true)
        public Page<ShowtimeDTO> getUpcoming(Pageable pageable) {

            return showtimeRepository.findUpComingWithMovieAndHall(
                    LocalDateTime.now(), pageable).map(showtime1 -> showtimeMapper.toDTO(showtime1));
        }

        @Transactional(readOnly = true)
        public List<ShowtimeDTO> getUpcomingByMovie(Long movieId) {
            if (movieId != null) {
                return showtimeMapper.toDTOList(showtimeRepository.findByMovie_idAndStartTimeAfter(movieId
                        , LocalDateTime.now()));
            }
            return List.of();
        }

        @Transactional
        public ShowtimeDTO create(ShowtimeCreateDTO showtimeCreateDTO) {
            Movie movie = movieRepository.findById(showtimeCreateDTO.getMovieId())
                    .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

            Hall hall = hallRepository.findById(showtimeCreateDTO.getHallId())
                    .orElseThrow(() -> new ResourceNotFoundException("Hall not found"));

            Showtime showtime = showtimeMapper.toEntity(showtimeCreateDTO);
            showtime.setHall(hall);
            showtime.setMovie(movie);

            Showtime saved = showtimeRepository.save(showtime);
            return showtimeMapper.toDTO(saved);
        }

        @Transactional
        public ShowtimeDTO update(Long id, ShowtimeCreateDTO showtimeCreateDTO) {
            Movie movie = movieRepository.findById(showtimeCreateDTO.getMovieId())
                    .orElseThrow(() -> new ResourceNotFoundException("Movie not found"));

            Hall hall = hallRepository.findById(showtimeCreateDTO.getHallId())
                    .orElseThrow(() -> new ResourceNotFoundException("Hall not found"));

            Showtime showtime = showtimeRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Showtime not found"));
            showtime.setHall(hall);
            showtime.setMovie(movie);
            showtimeMapper.updateEntityFromDTO(showtimeCreateDTO, showtime);

            Showtime saved = showtimeRepository.save(showtime);
            return showtimeMapper.toDTO(saved);
        }

        @Transactional
        public void delete(Long id) {
            Showtime showtime = showtimeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Showtime not found"));

            showtimeRepository.deleteById(id);
        }
    }
