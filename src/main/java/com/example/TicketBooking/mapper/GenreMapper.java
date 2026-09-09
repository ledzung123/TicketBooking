package com.example.TicketBooking.mapper;

import com.example.TicketBooking.dto.GenreDTO;
import com.example.TicketBooking.entity.Genre;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface GenreMapper {
    GenreDTO toDTO(Genre genre);
    Genre toEntity(GenreDTO genreDTO);
    List<GenreDTO> toDTOList(List<Genre> genres);
}
