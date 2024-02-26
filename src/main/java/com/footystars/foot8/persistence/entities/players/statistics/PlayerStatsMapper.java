package com.footystars.foot8.persistence.entities.players.statistics;

import com.footystars.foot8.api.model.players.model.statistics.PlayerStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerStatsMapper {

    @Mapping(target = "team", source = "team")
    @Mapping(target = "league", source = "league")
    @Mapping(target = "games", source = "games")
    @Mapping(target = "substitutes", source = "substitutes")
    @Mapping(target = "shots", source = "shots")
    @Mapping(target = "goals", source = "goals")
    @Mapping(target = "passes", source = "passes")
    @Mapping(target = "tackles", source = "tackles")
    @Mapping(target = "duels", source = "duels")
    @Mapping(target = "dribbles", source = "dribbles")
    @Mapping(target = "fouls", source = "fouls")
    @Mapping(target = "cards", source = "cards")
    @Mapping(target = "penalty", source = "penalty")
    PlayerStats toEntity(PlayerStatistics playerStatistics);

    PlayerStatistics toDto(PlayerStats playerStatisticsEntity);


}