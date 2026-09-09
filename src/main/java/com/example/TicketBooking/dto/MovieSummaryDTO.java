package com.example.TicketBooking.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieSummaryDTO {
    private Long id;
    private String title;
    private String posterUrl;
}
