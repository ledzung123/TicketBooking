package com.example.TicketBooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CinemaDTO {
    private Long id;

    @NotBlank(message = "Cinema name must not be blank")
    @Size(max = 150, message = "Cinema name must not exceed 150 characters.")
    private String name;

    @Size(max = 255, message = "Address must not exceed 150 characters.")
    private String address;

    @Size(max = 100, message = "City must not exceed 150 characters.")
    private String city;
}
