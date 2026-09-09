package com.example.TicketBooking.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class HallDTO {
    private Long id;
    private String name;
    private Long cinemaId;
    private String cinemaName;
}
