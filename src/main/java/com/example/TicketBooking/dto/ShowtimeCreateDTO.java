package com.example.TicketBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.example.TicketBooking.validation.ValidTimeRange;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ValidTimeRange
public class ShowtimeCreateDTO {
    @Schema(description = "Screening start time", example = "2026-12-01T19:00:00")
    @NotNull(message = "Start time should exist")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @Schema(description = "Screening end time", example = "2026-12-01T21:30:00")
    @NotNull(message = "End time should exist")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @Schema(description = "Ticket price", example = "120000")
    @NotNull(message = "Price should exist")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be over 0")
    private BigDecimal price;

    @Schema(description = "Movie ID", example = "1")
    @NotNull(message = "Showtime must relate to a movie")
    private Long movieId;

    @Schema(description = "Hall ID", example = "2")
    @NotNull(message = "Showtime must happen in a hall")
    private Long hallId;
}
