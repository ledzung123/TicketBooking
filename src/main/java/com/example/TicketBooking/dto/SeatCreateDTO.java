package com.example.TicketBooking.dto;

import com.example.TicketBooking.entity.SeatType;
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
    @NotBlank(message = "Seat code must not be blank")
    @Size(max = 10, message = "Seat code must not exceed 50 characters")
    private String seatCode;

    private SeatType seatType;

    @NotNull(message = "Seat must choose hall")
    private Long hallId;
}
