package com.footystars.persistence.mapper;

import com.footystars.model.api.Fixtures;
import com.footystars.model.api.Odds;
import com.footystars.model.entity.Fixture;
import com.footystars.model.entity.FixtureEvent;
import com.footystars.model.entity.FixturePlayer;
import com.footystars.model.entity.FixtureStatistic;
import com.footystars.model.entity.Lineup;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {TeamMapper.class,  PlayerMapper.class})
public interface FixtureMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Fixture partialUpdate(Odds.OddsResponse.OddFixture fixtureDto, @MappingTarget Fixture fixture);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Fixture partialUpdate(Fixtures.FixtureDto fixtureDto, @MappingTarget Fixture fixture);

    Fixture toEntity(Odds.OddsResponse.OddFixture fixture);

    @Mapping(source = "fixture.fixtureId", target = "id")
    @Mapping(source = "bookmakers", target = "bets")
    Fixture toEntity(Odds.OddsResponse oddsResponse);

    @Mapping(source = "info.fixtureId", target = "id")
    @Mapping(source = "lineups", target = "lineups")
    @Mapping(source = "events", target = "events")
    @Mapping(source = "statistics", target = "statistics")
    @Mapping(source = "teamPlayers", target = "players")
    Fixture toEntity(Fixtures.FixtureDto fixtureDto);

    List<Lineup> toLineupEntityList(List<Fixtures.FixtureDto.Lineup> lineups);

    Set<FixturePlayer> toPlayerEntityList(Set<Fixtures.FixtureDto.FixturePlayer> players);

    List<FixtureEvent> toEventEntityList(List<Fixtures.FixtureDto.FixtureEvent> events);

    List<FixtureStatistic> toStatistcsEntityList(List<Fixtures.FixtureDto.Statistics.Statistic> statistics);

}