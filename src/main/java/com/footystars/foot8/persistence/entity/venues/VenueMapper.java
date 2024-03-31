package com.footystars.foot8.persistence.entity.venues;

import com.footystars.foot8.persistence.entity.club.ClubMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        ClubMapper.class
})
public interface VenueMapper {

    Venue toEntity(VenueDto venueDto);

    VenueDto toDto(Venue venue);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Venue partialUpdate(VenueDto venueDto, @MappingTarget Venue venue);
}