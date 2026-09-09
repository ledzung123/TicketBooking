package com.example.TicketBooking.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponseDTO {
    private Long id;
    private String title;
    private String posterUrl;
    private String description;
    private Integer durationMinutes;
    private LocalDateTime releaseDate;
    private List<String> genreNames;
}
