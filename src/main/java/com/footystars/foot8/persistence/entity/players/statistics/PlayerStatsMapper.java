package com.footystars.foot8.persistence.entity.players.statistics;

import com.footystars.foot8.api.model.players.statistics.PlayerStatisticApi;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerStatsMapper {

    PlayerStats toEntity(PlayerStatsDto playerStatsDto);

    @Mapping(source = "games.appearances", target = "appearances")
    @Mapping(source = "games.lineups", target = "lineups")
    @Mapping(source = "games.minutes", target = "minutes")
    @Mapping(source = "games.number", target = "number")
    @Mapping(source = "games.position", target = "position")
    @Mapping(source = "games.rating", target = "rating")
    @Mapping(source = "games.captain", target = "captain")
    @Mapping(source = "substitutes.substitutedIn", target = "substitutedIn")
    @Mapping(source = "substitutes.substitutedOut", target = "substitutedOut")
    @Mapping(source = "substitutes.bench", target = "bench")
    @Mapping(source = "shots.onTarget", target = "shotsOnTarget")
    @Mapping(source = "shots.total", target = "shots")
    @Mapping(source = "goals.total", target = "goals")
    @Mapping(source = "goals.conceded", target = "goalsConceded")
    @Mapping(source = "goals.assists", target = "assists")
    @Mapping(source = "goals.saves", target = "saves")
    @Mapping(source = "passes.total", target = "passes")
    @Mapping(source = "passes.key", target = "keyPasses")
    @Mapping(source = "passes.accuracy", target = "passesAccuracy")
    @Mapping(source = "tackles.total", target = "tackles")
    @Mapping(source = "tackles.blocks", target = "blocks")
    @Mapping(source = "tackles.interceptions", target = "interceptions")
    @Mapping(source = "duels.total", target = "duels")
    @Mapping(source = "duels.won", target = "duelsWon")
    @Mapping(source = "dribbles.attempts", target = "dribblesAttempts")
    @Mapping(source = "dribbles.success", target = "dribblesSuccess")
    @Mapping(source = "dribbles.past", target = "dribblesPast")
    @Mapping(source = "fouls.drawn", target = "foulsDrawn")
    @Mapping(source = "fouls.committed", target = "foulsCommitted")
    @Mapping(source = "cards.yellow", target = "yellowCards")
    @Mapping(source = "cards.yellowRed", target = "yellowRedCards")
    @Mapping(source = "cards.red", target = "redCards")
    @Mapping(source = "penalty.won", target = "penaltiesWon")
    @Mapping(source = "penalty.committed", target = "penaltiesCommitted")
    @Mapping(source = "penalty.scored", target = "penaltiesScored")
    @Mapping(source = "penalty.missed", target = "penaltiesMissed")
    @Mapping(source = "penalty.saved", target = "penaltiesSaved")
    PlayerStatsDto apiToDto(PlayerStatisticApi playerStatistic);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(PlayerStatsDto playerStatsDto, @MappingTarget PlayerStats playerStats);


    PlayerStatsDto toDto(PlayerStats playerStats);
}