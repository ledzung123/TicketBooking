package com.example.TicketBooking.dto;

import com.example.TicketBooking.entity.SeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatDTO {
    @Schema(description = "Seat ID", example = "5")
    private Long id;
    @Schema(description = "Seat label within the hall", example = "A1")
    private String seatCode;
    @Schema(description = "Seat category", example = "STANDARD")
    private SeatType seatType;
    @Schema(description = "Hall containing the seat", example = "2")
    private Long hallId;
}
