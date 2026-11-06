package com.example.TicketBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingCreateDTO {
    @Schema(description = "Showtime to book", example = "12")
    @NotNull(message = "Must choose showtime")
    private Long showtimeId;

    @Schema(description = "IDs of seats to reserve", example = "[5, 6]")
    @NotEmpty(message = "Must choose at least one seat")
    private List<Long> seatIds;
}
