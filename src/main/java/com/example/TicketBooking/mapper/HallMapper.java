package com.example.TicketBooking.mapper;

import com.example.TicketBooking.dto.HallCreateDTO;
import com.example.TicketBooking.dto.HallDTO;
import com.example.TicketBooking.entity.Hall;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
        , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface HallMapper {
    @Mapping(target = "cinemaId", source = "cinema.id")
    @Mapping(target = "cinemaName", source = "cinema.name")
    HallDTO toDTO(Hall hall);

    @Mapping(target = "cinema", ignore = true)
    Hall toEntity(HallCreateDTO hallCreateDTO);

    List<HallDTO> toDTOList(List<Hall> hall);

    void updateEntityFromDTO(HallCreateDTO hallCreateDTO, @MappingTarget Hall hall);
}
