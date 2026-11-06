package com.example.TicketBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieCreateDTO {
    @Schema(description = "Movie title", example = "Inception")
    @NotBlank(message = "Title must not be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters.")
    private String title;

    @Schema(description = "Poster image URL", example = "https://example.com/poster.jpg")
    @Size(max = 2000, message = "Poster URL must not exceed 2000 characters.")
    private String posterUrl;

    @Schema(description = "Movie synopsis", example = "A team enters shared dreams.")
    @Size(max = 2000, message = "Description must not exceed 2000 characters.")
    private String description;

    @Schema(description = "Runtime in minutes", example = "148")
    @NotNull
    @Min(value = 1, message = "Duration must be over 0 minutes")
    private Integer durationMinutes;

    @Schema(description = "Release date and time", example = "2010-07-16T00:00:00")
    private LocalDateTime releaseDate;

    @Schema(description = "Genre IDs", example = "[1, 2]")
    @NotEmpty(message = "Must choose at least 1 genre")
    private List<Long> genreIds;
}
