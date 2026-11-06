package com.example.TicketBooking.dto;

import com.example.TicketBooking.entity.SeatType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatCreateDTO {
    @Schema(description = "Seat label within the hall", example = "A1")
    @NotBlank(message = "Seat code must not be blank")
    @Size(max = 10, message = "Seat code must not exceed 50 characters")
    private String seatCode;

    @Schema(description = "Seat category", example = "STANDARD")
    private SeatType seatType;

    @Schema(description = "Hall containing the seat", example = "2")
    @NotNull(message = "Seat must choose hall")
    private Long hallId;
}
