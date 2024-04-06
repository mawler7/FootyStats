package com.footystars.foot8.mapper;

import com.footystars.foot8.buisness.model.entity.Venue;
import com.footystars.foot8.buisness.model.dto.VenueDto;
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