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
public class CinemaDTO {
    @Schema(description = "Cinema ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Cinema name", example = "Central Cinema")
    @NotBlank(message = "Cinema name must not be blank")
    @Size(max = 150, message = "Cinema name must not exceed 150 characters.")
    private String name;

    @Schema(description = "Street address", example = "123 Main Street")
    @Size(max = 255, message = "Address must not exceed 150 characters.")
    private String address;

    @Schema(description = "City", example = "Hanoi")
    @Size(max = 100, message = "City must not exceed 150 characters.")
    private String city;
}
