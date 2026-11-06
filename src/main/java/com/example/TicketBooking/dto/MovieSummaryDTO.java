package com.example.TicketBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieSummaryDTO {
    @Schema(description = "Movie ID", example = "1")
    private Long id;
    @Schema(description = "Movie title", example = "Inception")
    private String title;
    @Schema(description = "Poster image URL", example = "https://example.com/poster.jpg")
    private String posterUrl;
}
