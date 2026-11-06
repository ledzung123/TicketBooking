package com.example.TicketBooking.dto;

import com.example.TicketBooking.entity.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingSummaryDTO {
    @Schema(description = "Booking ID", example = "42")
    private Long id;
    @Schema(description = "Time the booking was created", example = "2026-12-01T18:30:00")
    private LocalDateTime bookingTime;
    @Schema(description = "Total booking price", example = "240000")
    private BigDecimal totalPrice;
    @Schema(description = "Current booking status", example = "CONFIRMED")
    private Status status;
    @Schema(description = "Booked movie title", example = "Inception")
    private String movieTitle;
    @Schema(description = "Number of booked seats", example = "2")
    private Integer seatCount;
}
