package com.footystars.foot8.persistence.entities.venues;


import com.footystars.foot8.api.model.venue.model.VenueDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VenueMapper {


    @Mapping(source = "venueName", target = "name")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "capacity", target = "capacity")
    @Mapping(source = "surface", target = "surface")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "venueId", target = "id")
    Venue toEntity(VenueDto venueDto);


    @Mapping(source = "name", target = "venueName")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "city", target = "city")
    @Mapping(source = "country", target = "country")
    @Mapping(source = "capacity", target = "capacity")
    @Mapping(source = "surface", target = "surface")
    @Mapping(source = "image", target = "image")
    @Mapping(source = "id", target = "venueId")
    VenueDto toDto(Venue venueEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Venue partialUpdate(VenueDto venueDto, @MappingTarget Venue venue);
}