/*
package com.example.TicketBooking.runner;

import com.example.TicketBooking.entity.*;
import com.example.TicketBooking.repository.*;
import com.example.TicketBooking.service.BookingService;
import com.example.TicketBooking.service.MovieService;
import com.example.TicketBooking.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class TestRunner implements CommandLineRunner {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final HallRepository hallRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;

    private final MovieService movieService;
    private final ShowtimeService showtimeService;
    private final BookingService bookingService;

    @Override
    @Transactional // Bắt buộc có Transactional khi lưu các entity có quan hệ
    public void run(String... args) {

        System.out.println("\n====== 1. BẮT ĐẦU TẠO DATA GIẢ ======");

        // 1. Tạo Genre
        Genre action = new Genre();
        action.setName("Action");
        genreRepository.save(action);

        Genre sciFi = new Genre();
        sciFi.setName("Sci-Fi");
        genreRepository.save(sciFi);

        // 2. Tạo Movie kèm Genre (Quan hệ N-N)
        Movie inception = new Movie();
        inception.setTitle("Inception");
        inception.setDurationMinutes(150);
        inception.setReleaseDate(LocalDateTime.of(2010, 7, 16, 13, 30));
        // Gán Genre cho Movie
        Set<Genre> genres = new HashSet<>();
        genres.add(action);
        genres.add(sciFi);
        inception.setGenres(genres); // Tên field của bạn có thể khác, chỉnh lại cho đúng
        movieRepository.save(inception);

        // 3. Tạo Cinema & Hall
        Cinema cgv = new Cinema();
        cgv.setName("CGV Diamond");
        cgv.setCity("Hanoi");
        cinemaRepository.save(cgv);

        Hall hall1 = new Hall();
        hall1.setName("Hall 1");
        hall1.setCinema(cgv);
        hallRepository.save(hall1);

        // 4. Tạo Showtime (gắn Movie và Hall)
        Showtime showtime1 = new Showtime();
        showtime1.setMovie(inception);
        showtime1.setHall(hall1);
        showtime1.setStartTime(LocalDateTime.now());
        showtime1.setEndTime(LocalDateTime.now().plusMinutes(150));
        showtime1.setPrice(new BigDecimal("100000"));
        showtimeRepository.save(showtime1);

        // 5. Tạo Seat (Ghế)
        Seat seatA1 = new Seat();
        seatA1.setSeatCode("A1");
        seatA1.setSeatType(SeatType.STANDARD); // Dùng Enum của bạn
        seatA1.setHall(hall1);
        seatRepository.save(seatA1);

        // 6. Tạo User
        User customer = new User();
        customer.setEmail("customer@test.com");
        customer.setPassword("123456");
        customer.setFullName("Nguyen Van A");
        customer.setRole(Role.CUSTOMER); // Dùng Enum của bạn
        userRepository.save(customer);

        // 7. Tạo Booking & BookingSeat (Quan hệ 1-N)
        Booking booking = new Booking();
        booking.setUser(customer);
        booking.setShowtime(showtime1);
        booking.setTotalPrice(new BigDecimal("100000"));
        booking.setStatus(Status.CONFIRMED); // Dùng Enum của bạn
        bookingRepository.save(booking);

        BookingSeat bookingSeat = new BookingSeat();
        bookingSeat.setBooking(booking);
        bookingSeat.setSeat(seatA1);
        bookingSeat.setPrice(new BigDecimal("100000"));
        bookingSeatRepository.save(bookingSeat);
        // Nhờ có @PrePersist, showtimeId sẽ tự động được đồng bộ từ booking.showtime

        System.out.println("====== TẠO DATA XONG! BẮT ĐẦU TEST N+1 ======");

        // =================================================================
        // 2. CHẠY TEST N+1 (Nhìn vào Console Log xem có bị tách query không)
        // =================================================================

        System.out.println("\n>>> TEST 1: Movie Repository (Kỳ vọng: 1 câu SELECT có JOIN)");
        movieService.getAllMoviesWithGenres(); // Đổi tên hàm cho đúng với code bạn viết

        System.out.println("\n>>> TEST 2: Showtime Repository (Kỳ vọng: 1 câu SELECT có JOIN movie và hall)");
        showtimeService.getAllShowtimeWithMovieAndHall(); // Đổi tên hàm cho đúng

        System.out.println("\n>>> TEST 3: Booking Repository (Kỳ vọng: 1 câu SELECT có JOIN booking_seats)");
        bookingService.getAllBookingsWithBookingSeats(); // Đổi tên hàm cho đúng

        System.out.println("\n====== KẾT THÚC TEST ======\n");
    }
}*/
