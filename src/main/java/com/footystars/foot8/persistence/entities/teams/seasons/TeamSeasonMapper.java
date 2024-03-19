package com.footystars.foot8.persistence.entities.teams.seasons;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TeamSeasonMapper {
    TeamSeason toEntity(TeamSeasonDto teamSeasonDto);

    TeamSeasonDto toDto(TeamSeason teamSeason);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TeamSeason partialUpdate(TeamSeasonDto teamSeasonDto, @MappingTarget TeamSeason teamSeason);
}