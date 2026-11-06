package com.example.TicketBooking.controller;

import com.example.TicketBooking.dto.BookingCreateDTO;
import com.example.TicketBooking.dto.BookingResponseDTO;
import com.example.TicketBooking.dto.BookingSummaryDTO;
import com.example.TicketBooking.entity.Status;
import com.example.TicketBooking.exception.ResourceNotFoundException;
import com.example.TicketBooking.repository.UserRepository;
import com.example.TicketBooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@Tag(name = "Booking", description = "Book tickets, view bookings, and cancel bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;
    private final UserRepository userRepository;

    @GetMapping("/my-bookings")
    @Operation(summary = "List my bookings")
    public ResponseEntity<List<BookingSummaryDTO>> getUserBookings(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Long userId = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found")).getId();

        List<BookingSummaryDTO> bookingSummaryDTOS = bookingService.getMyBookings(userId);

        return ResponseEntity.ok(bookingSummaryDTOS);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get booking details")
    public ResponseEntity<BookingResponseDTO> getDetailBookings(@PathVariable Long id
            , @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        Long userId = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found")).getId();

        BookingResponseDTO bookingResponseDTO = bookingService.getBookingDetail(id, userId);
        return ResponseEntity.ok(bookingResponseDTO);
    }

    @GetMapping
    @Operation(summary = "List all bookings")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<BookingSummaryDTO>> getBookingsForAdmin(
            @RequestParam(required = false) Status status,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<BookingSummaryDTO> page = bookingService.getAllBookingsForAdmin(status, pageable);
        return ResponseEntity.ok(page);
    }

    @PostMapping
    @Operation(summary = "Create a booking")
    public ResponseEntity<BookingResponseDTO> createBooking(@AuthenticationPrincipal UserDetails userDetails
            , @Valid @RequestBody BookingCreateDTO bookingCreateDTO) {
        String email = userDetails.getUsername();
        Long userId = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found")).getId();

        log.info("User {} is creating booking for showtime {}", userId, bookingCreateDTO.getShowtimeId());

        BookingResponseDTO bookingResponseDTO = bookingService.createBooking(userId, bookingCreateDTO);

        log.info("Booking created successfully with ID: {}", bookingResponseDTO.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(bookingResponseDTO);
    }

    @PatchMapping("{id}/cancel")
    @Operation(summary = "Cancel a booking")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@AuthenticationPrincipal UserDetails userDetails
            , @PathVariable Long id) {
        String email = userDetails.getUsername();
        Long userId = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found")).getId();

        log.info("User {} is cancelling booking {}", userId, id);

        BookingResponseDTO bookingResponseDTO = bookingService.cancelBooking(id, userId);

        log.info("Booking {} cancelled successfully", id);

        return ResponseEntity.ok(bookingResponseDTO);
    }


}
