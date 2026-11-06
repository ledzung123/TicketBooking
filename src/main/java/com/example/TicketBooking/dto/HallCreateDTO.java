package com.example.TicketBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class HallCreateDTO {
    @Schema(description = "Screening hall name", example = "Hall 1")
    @NotBlank(message = "Hall name must not be blank")
    @Size(max = 50, message = "Hall name must not exceed 50 characters")
    private String name;

    @Schema(description = "Cinema that contains the hall", example = "1")
    @NotNull(message = "Must choose cinema")
    private Long cinemaId;
}
