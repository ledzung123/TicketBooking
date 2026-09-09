package com.example.TicketBooking.mapper;

import com.example.TicketBooking.dto.ShowtimeCreateDTO;
import com.example.TicketBooking.dto.ShowtimeDTO;
import com.example.TicketBooking.entity.Showtime;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE
        , nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ShowtimeMapper {
    @Mapping(target = "movieId", source = "movie.id")
    @Mapping(target = "movieTitle", source = "movie.title")
    @Mapping(target = "hallId", source = "hall.id")
    @Mapping(target = "hallName", source = "hall.name")
    ShowtimeDTO toDTO(Showtime showtime);

    @Mapping(target = "movie", ignore = true)
    @Mapping(target = "hall", ignore = true)
    Showtime toEntity(ShowtimeCreateDTO showtimeCreateDTO);

    List<ShowtimeDTO> toDTOList(List<Showtime> showtime);

    void updateEntityFromDTO(ShowtimeCreateDTO showtimeCreateDTO, @MappingTarget Showtime showtime);
}
