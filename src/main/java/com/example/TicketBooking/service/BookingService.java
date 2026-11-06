package com.example.TicketBooking.service;

import com.example.TicketBooking.dto.BookingCreateDTO;
import com.example.TicketBooking.dto.BookingResponseDTO;
import com.example.TicketBooking.dto.BookingSummaryDTO;
import com.example.TicketBooking.entity.*;
import com.example.TicketBooking.exception.BusinessException;
import com.example.TicketBooking.exception.ResourceInUseException;
import com.example.TicketBooking.exception.ResourceNotFoundException;
import com.example.TicketBooking.mapper.BookingMapper;
import com.example.TicketBooking.mapper.BookingSeatMapper;
import com.example.TicketBooking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final BookingSeatMapper bookingSeatMapper;

    public List<Booking> getAllBookingsWithBookingSeats() {
        return bookingRepository.findAllWithBookingSeats();
    }

    public Optional<Booking> getBookingByIdWithBookingSeats(Long bookingId) {
        return bookingRepository.findByIdWithBookingSeats(bookingId);
    }

    @Transactional
    public BookingResponseDTO createBooking(Long userId, BookingCreateDTO bookingCreateDTO) {
        Showtime showtime = showtimeRepository.findById(bookingCreateDTO.getShowtimeId())
                .orElseThrow(() -> new ResourceNotFoundException("Showtime not found"));

        if (showtime.getStartTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Showtime has begun, yon cant book ticket for this showtime");
        }

        List<Seat> seats = seatRepository.findAllById(bookingCreateDTO.getSeatIds());

        if (seats.size() != bookingCreateDTO.getSeatIds().size()) {
            throw new ResourceNotFoundException("Some seats do not exist");
        }

        for (Seat seat : seats) {
            if (!seat.getHall().getId().equals(showtime.getHall().getId())) {
                throw new BusinessException("Seat does not belong to the hall of this showtime");
            }
        }

        for (Seat seat : seats) {
            if (bookingSeatRepository.existsByShowtimeIdAndSeat_IdAndBooking_StatusNot(
                    showtime.getId(), seat.getId(), Status.CANCELLED)) {
                throw new BusinessException("Seat " + seat.getSeatCode() + "has been booked");
            }
        }

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Seat seat : seats) {
            if (seat.getSeatType() == SeatType.STANDARD) {
                totalPrice = totalPrice.add(showtime.getPrice());
            }

            else if (seat.getSeatType() == SeatType.VIP) {
                totalPrice =  totalPrice.add(showtime.getPrice().multiply(new BigDecimal("1.5")));
            }

            else {
                totalPrice = totalPrice.add(showtime.getPrice().multiply(new BigDecimal("2.0")));
            }
        }

        Booking booking = new Booking();
        booking.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));

        booking.setShowtime(showtime);
        booking.setTotalPrice(totalPrice);
        booking.setStatus(Status.PENDING);

        bookingRepository.save(booking);

        List<BookingSeat> bookingSeats = new ArrayList<>();

        for (Seat seat : seats) {
            BookingSeat bookingSeat = new BookingSeat();
            bookingSeat.setBooking(booking);
            bookingSeat.setSeat(seat);
            bookingSeat.setShowtimeId(bookingCreateDTO.getShowtimeId());
            if (seat.getSeatType() == SeatType.STANDARD) {
                bookingSeat.setPrice(showtime.getPrice());
            }

            else if (seat.getSeatType() == SeatType.VIP) {
                bookingSeat.setPrice(showtime.getPrice().multiply(new BigDecimal("1.5")));
            }

            else {
                bookingSeat.setPrice(showtime.getPrice().multiply(new BigDecimal("2.0")));
            }

            bookingSeats.add(bookingSeat);
        }

        bookingSeatRepository.saveAll(bookingSeats);

        booking.setBookingSeats(bookingSeats);

        return bookingMapper.toResponseDTO(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingSummaryDTO> getMyBookings(Long userId) {
        List<Booking> bookings = bookingRepository.findByUser_IdOrderByBookingTimeDesc(userId);

        List<BookingSummaryDTO> bookingSummaryDTOS = bookingMapper.toSummaryDTOList(bookings);

        return bookingSummaryDTOS;
    }

    @Transactional(readOnly = true)
    public BookingResponseDTO getBookingDetail(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findByIdWithBookingSeats(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (!booking.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("User not found");
        }

        return bookingMapper.toResponseDTO(booking);
    }

    @Transactional(readOnly = true)
    public Page<BookingSummaryDTO> getAllBookingsForAdmin(Status status, Pageable pageable) {
        Page<Booking> bookings;

        if (status != null) {
            bookings = bookingRepository.findByStatus(status, pageable);
        } else {
            bookings = bookingRepository.findAll(pageable);
        }

        return bookings.map(bookingMapper::toSummaryDTO);
    }

    @Transactional
    public BookingResponseDTO cancelBooking(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findByIdWithBookingSeats(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (!booking.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Booking not found or access denied");
        }

        if (booking.getStatus() == Status.CANCELLED) {
            throw new BusinessException("Booking has already been cancelled");
        }

        if (booking.getShowtime().getStartTime().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Showtime has begun, yon cant cancel ticket for this showtime");
        }

        booking.setStatus(Status.CANCELLED);
        bookingRepository.save(booking);

        return bookingMapper.toResponseDTO(booking);
    }
}
