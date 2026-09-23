package com.example.TicketBooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreDTO {
    private Long id;

    @NotBlank(message = "Genre name should not be blank")
    @Size(max = 50, message = "Category name must not exceed 50 characters.")
    private String name;
}
