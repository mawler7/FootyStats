package com.example.foot8.persistence.entities.venues;

import com.example.foot8.api.venue.model.VenueDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VenueMapper {
    VenueEntity toEntity(VenueDto venueDto);

    VenueDto toDto(VenueEntity venueEntity);


}