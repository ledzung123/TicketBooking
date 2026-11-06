package com.example.TicketBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieResponseDTO {
    @Schema(description = "Movie ID", example = "1")
    private Long id;
    @Schema(description = "Movie title", example = "Inception")
    private String title;
    @Schema(description = "Poster image URL", example = "https://example.com/poster.jpg")
    private String posterUrl;
    @Schema(description = "Movie synopsis", example = "A team enters shared dreams.")
    private String description;
    @Schema(description = "Runtime in minutes", example = "148")
    private Integer durationMinutes;
    @Schema(description = "Release date and time", example = "2010-07-16T00:00:00")
    private LocalDateTime releaseDate;
    @Schema(description = "Names of the movie's genres", example = "[\"Action\", \"Sci-Fi\"]")
    private List<String> genreNames;
}
