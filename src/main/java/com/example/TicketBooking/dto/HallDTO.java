package com.example.TicketBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class HallDTO {
    @Schema(description = "Hall ID", example = "2")
    private Long id;
    @Schema(description = "Screening hall name", example = "Hall 1")
    private String name;
    @Schema(description = "Parent cinema ID", example = "1")
    private Long cinemaId;
    @Schema(description = "Parent cinema name", example = "Central Cinema")
    private String cinemaName;
}
