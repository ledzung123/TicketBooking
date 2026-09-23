package com.example.TicketBooking.dto;

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
    @NotBlank(message = "Hall name must not be blank")
    @Size(max = 50, message = "Hall name must not exceed 50 characters")
    private String name;

    @NotNull(message = "Must choose cinema")
    private Long cinemaId;
}
