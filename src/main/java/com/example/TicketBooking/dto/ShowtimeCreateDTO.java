package com.example.TicketBooking.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowtimeCreateDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BigDecimal price;
    private Long movieId;
    private Long hallId;
}
