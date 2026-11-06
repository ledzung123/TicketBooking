package com.example.TicketBooking.mapper;


import com.example.TicketBooking.dto.*;
import com.example.TicketBooking.entity.Booking;
import com.example.TicketBooking.entity.BookingSeat;
import com.example.TicketBooking.entity.Movie;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
        , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
        , uses = {BookingSeatMapper.class})
public interface BookingMapper {
    @Mapping(target = "movieTitle", source = "showtime.movie.title")
    @Mapping(target = "bookingTime", source = "createdAt")
    @Mapping(target = "seatCount"
            , expression = "java(booking.getBookingSeats() != null ? booking.getBookingSeats().size() : 0)")
    BookingSummaryDTO toSummaryDTO(Booking booking);
    List<BookingSummaryDTO> toSummaryDTOList(List<Booking> bookings);

    @Mapping(target = "movieTitle", source = "showtime.movie.title")
    @Mapping(target = "bookingTime", source = "createdAt")
    @Mapping(target = "showtimeStartTime", source = "showtime.startTime")
    @Mapping(target = "cinemaName", source = "showtime.hall.cinema.name")
    @Mapping(target = "hallName", source = "showtime.hall.name")
    @Mapping(target = "seats", source = "bookingSeats")
    BookingResponseDTO toResponseDTO(Booking booking);

    void updateEntityFromDTO(BookingCreateDTO bookingCreateDTO, @MappingTarget Booking booking);
}
