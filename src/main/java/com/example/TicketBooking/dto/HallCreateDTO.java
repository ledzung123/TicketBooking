package com.example.TicketBooking.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class HallCreateDTO {
    private String name;
    private Long cinemaId;
}
