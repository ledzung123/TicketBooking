package com.example.TicketBooking.dto;

import com.example.TicketBooking.entity.SeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingSeatDTO {
    @Schema(description = "Seat ID", example = "5")
    private Long seatId;
    @Schema(description = "Seat label within the hall", example = "A1")
    private String seatCode;
    @Schema(description = "Seat category", example = "STANDARD")
    private SeatType seatType;
    @Schema(description = "Price paid for this seat", example = "120000")
    private BigDecimal price;
}
