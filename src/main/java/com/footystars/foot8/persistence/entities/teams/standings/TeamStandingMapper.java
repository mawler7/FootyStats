package com.footystars.foot8.persistence.entities.teams.standings;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeamStandingMapper {


    Standing toEntity(TeamStandingDto teamStandingDto);

    TeamStandingDto toDto(Standing teamStanding);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Standing partialUpdate(TeamStandingDto teamStandingDto, @MappingTarget Standing teamStanding);
}