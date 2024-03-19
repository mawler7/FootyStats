package com.footystars.foot8.persistence.entities.leagues.seaon;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LeagueSeasonMapper {
    LeagueSeason toEntity(LeagueSeasonDto leagueSeasonDto);

    LeagueSeasonDto toDto(LeagueSeason leagueSeason);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    LeagueSeason partialUpdate(LeagueSeasonDto leagueSeasonDto, @MappingTarget LeagueSeason leagueSeason);
}