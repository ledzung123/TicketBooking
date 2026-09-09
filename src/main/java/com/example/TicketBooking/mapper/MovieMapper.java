package com.example.TicketBooking.mapper;

import com.example.TicketBooking.dto.MovieCreateDTO;
import com.example.TicketBooking.dto.MovieResponseDTO;
import com.example.TicketBooking.dto.MovieSummaryDTO;
import com.example.TicketBooking.entity.Movie;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
        , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovieMapper {
    MovieSummaryDTO toSummaryDTO(Movie movie);
    List<MovieSummaryDTO> toSummaryDTOList(List<Movie> movies);

    @Mapping(target = "genres", ignore = true)
    Movie toEntity(MovieCreateDTO movieCreateDTO);

    @Mapping(target = "genreNames"
            , expression = "java(movie.getGenres().stream()" +
            ".map(g -> g.getName()).collect(java.util.stream.Collectors.toList()))")
    MovieResponseDTO toResponseDTO(Movie movie);

    @Mapping(target = "genres", ignore = true)
    void updateEntityFromDTO(MovieCreateDTO movieCreateDTO, @MappingTarget Movie movie);

}
