package com.footystars.foot8.persistence.entities.venues;

import com.footystars.foot8.persistence.entities.teams.team.TeamMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {TeamMapper.class})
public interface VenueMapper {

    Venue toEntity(VenueDto venueDto);

    VenueDto toDto(Venue venue);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Venue partialUpdate(VenueDto venueDto, @MappingTarget Venue venue);
}