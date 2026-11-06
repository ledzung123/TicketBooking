package com.example.TicketBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreDTO {
    @Schema(description = "Genre ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Genre name", example = "Action")
    @NotBlank(message = "Genre name should not be blank")
    @Size(max = 50, message = "Category name must not exceed 50 characters.")
    private String name;
}
