package com.example.TicketBooking.mapper;

import com.example.TicketBooking.dto.HallCreateDTO;
import com.example.TicketBooking.dto.HallDTO;
import com.example.TicketBooking.dto.SeatCreateDTO;
import com.example.TicketBooking.dto.SeatDTO;
import com.example.TicketBooking.entity.Hall;
import com.example.TicketBooking.entity.Seat;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
        , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SeatMapper {
    @Mapping(target = "hallId", source = "hall.id")
    SeatDTO toDTO(Seat seat);

    @Mapping(target = "hall", ignore = true)
    Seat toEntity(SeatCreateDTO seatCreateDTO);

    List<SeatDTO> toDTOList(List<Seat> seats);

    void updateEntityFromDTO(SeatCreateDTO seatCreateDTO, @MappingTarget Seat seat);

}
