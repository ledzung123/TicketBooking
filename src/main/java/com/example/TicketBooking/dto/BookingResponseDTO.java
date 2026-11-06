package com.example.TicketBooking.dto;

import com.example.TicketBooking.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponseDTO {
    @Schema(description = "Booking ID", example = "42")
    private Long id;
    @Schema(description = "Time the booking was created", example = "2026-12-01T18:30:00")
    private LocalDateTime bookingTime;
    @Schema(description = "Total price of all booked seats", example = "240000")
    private BigDecimal totalPrice;
    @Schema(description = "Current booking status", example = "CONFIRMED")
    private Status status;
    @Schema(description = "Booked movie title", example = "Inception")
    private String movieTitle;
    @Schema(description = "Start time of the booked showtime", example = "2026-12-01T19:00:00")
    private LocalDateTime showtimeStartTime;
    @Schema(description = "Cinema name", example = "Central Cinema")
    private String cinemaName;
    @Schema(description = "Screening hall name", example = "Hall 1")
    private String hallName;
    @Schema(description = "Seats included in this booking")
    private List<BookingSeatDTO> seats;
}
