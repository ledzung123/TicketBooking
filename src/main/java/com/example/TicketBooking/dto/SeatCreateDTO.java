package com.example.TicketBooking.dto;

import com.example.TicketBooking.entity.SeatType;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeatCreateDTO {
    private String seatCode;
    private SeatType seatType;
    private Long hallId;
}
