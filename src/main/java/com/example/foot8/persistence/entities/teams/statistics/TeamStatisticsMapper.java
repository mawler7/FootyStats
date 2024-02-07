package com.example.foot8.persistence.entities.teams.statistics;

import com.example.foot8.api.teams.statistics.model.TeamStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamStatisticsMapper {


     @Mapping(target = "biggest", source = "biggest")
     @Mapping(target = "cards", source = "cards")
     @Mapping(target = "cleanSheet", source = "cleanSheet")
     @Mapping(target = "failedToScore", source = "failedToScore")
     @Mapping(target = "goals", source = "goals")
     @Mapping(target = "fixtures", source = "fixtures")
     @Mapping(target = "league", source = "league")
     @Mapping(target = "lineups", source = "lineups")
     @Mapping(target = "penalty", source = "penalty")
     @Mapping(target = "team", source = "team")
    TeamStatisticsEntity toEntity(TeamStatistics teamStatistics);

    TeamStatistics toDto(TeamStatisticsEntity teamStatisticsEntity);



}