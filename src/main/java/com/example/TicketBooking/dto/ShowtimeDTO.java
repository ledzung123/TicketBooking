package com.example.TicketBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowtimeDTO {
    @Schema(description = "Showtime ID", example = "12")
    private Long id;
    @Schema(description = "Screening start time", example = "2026-12-01T19:00:00")
    private LocalDateTime startTime;
    @Schema(description = "Screening end time", example = "2026-12-01T21:30:00")
    private LocalDateTime endTime;
    @Schema(description = "Ticket price", example = "120000")
    private BigDecimal price;
    @Schema(description = "Movie ID", example = "1")
    private Long movieId;
    @Schema(description = "Movie title", example = "Inception")
    private String movieTitle;
    @Schema(description = "Hall ID", example = "2")
    private Long hallId;
    @Schema(description = "Hall name", example = "Hall 1")
    private String hallName;
}
