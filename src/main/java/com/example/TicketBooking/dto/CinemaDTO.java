package com.example.TicketBooking.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaDTO {
    private Long id;
    private String name;
    private String address;
    private String city;
}
