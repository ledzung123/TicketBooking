package com.example.TicketBooking.mapper;


import com.example.TicketBooking.dto.BookingSeatDTO;
import com.example.TicketBooking.dto.CinemaDTO;
import com.example.TicketBooking.entity.Booking;
import com.example.TicketBooking.entity.BookingSeat;
import com.example.TicketBooking.entity.Cinema;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
        , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookingSeatMapper {
    @Mapping(target = "seatId", source = "seat.id")
    @Mapping(target = "seatCode", source = "seat.seatCode")
    @Mapping(target = "seatType", source = "seat.seatType")
    BookingSeatDTO toDTO(BookingSeat bookingSeat);

    @Mapping(target = "seatId", source = "seat.id")
    @Mapping(target = "seatCode", source = "seat.seatCode")
    @Mapping(target = "seatType", source = "seat.seatType")
    List<BookingSeatDTO> toDTOList(List<BookingSeat> cinemaList);
    void updateEntityFromDTO(BookingSeatDTO bookingSeatDTO, @MappingTarget BookingSeat bookingSeat);
}
