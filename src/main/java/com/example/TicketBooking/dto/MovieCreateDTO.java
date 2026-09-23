package com.example.TicketBooking.dto;

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
    @NotBlank(message = "Title must not be blank")
    @Size(max = 255, message = "Title must not exceed 255 characters.")
    private String title;

    @Size(max = 2000, message = "Poster URL must not exceed 150 characters.")
    private String posterUrl;

    @Size(max = 2000, message = "Description must not exceed 2000 characters.")
    private String description;

    @NotNull
    @Min(value = 1, message = "Duration must be over 0 minutes")
    private Integer durationMinutes;

    private LocalDateTime releaseDate;

    @NotEmpty(message = "Must choose at least 1 genre")
    private List<Long> genreIds;
}
