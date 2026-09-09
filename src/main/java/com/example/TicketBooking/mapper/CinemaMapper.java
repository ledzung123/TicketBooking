package com.example.TicketBooking.mapper;

import com.example.TicketBooking.dto.CinemaDTO;
import com.example.TicketBooking.entity.Cinema;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
        , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CinemaMapper {
    CinemaDTO toDTO(Cinema cinema);
    Cinema toEntity(CinemaDTO cinemaDTO);
    List<CinemaDTO> toDTOList(List<Cinema> cinemaList);
    void updateEntityFromDTO(CinemaDTO cinemaDTO, @MappingTarget Cinema cinema);
}
