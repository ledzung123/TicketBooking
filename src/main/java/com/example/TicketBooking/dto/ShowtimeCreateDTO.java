package com.example.TicketBooking.dto;

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
    @NotNull(message = "Start time should exist")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "End time should exist")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @NotNull(message = "Price should exist")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be over 0")
    private BigDecimal price;

    @NotNull(message = "Showtime must relate to a movie")
    private Long movieId;

    @NotNull(message = "Showtime must happen in a hall")
    private Long hallId;
}
