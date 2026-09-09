package com.example.TicketBooking.service;

import com.example.TicketBooking.entity.Booking;
import com.example.TicketBooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;

    public List<Booking> getAllBookingsWithBookingSeats() {
        return bookingRepository.findAllWithBookingSeats();
    }

    public Optional<Booking> getBookingByIdWithBookingSeats(Long bookingId) {
        return bookingRepository.findByIdWithBookingSeats(bookingId);
    }
}
