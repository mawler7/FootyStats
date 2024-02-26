package com.footystars.foot8.persistence.entities.fixtures;

import com.footystars.foot8.api.model.fixture.FixtureDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface FixtureMapper {

    @Mapping(source = "fixture.id", target = "id")
    @Mapping(source = "fixture.referee", target = "referee")
    @Mapping(source = "fixture.timezone", target = "timezone")
    @Mapping(source = "fixture.date", target = "date")
    @Mapping(source = "fixture.timestamp", target = "timestamp")
    @Mapping(source = "fixture.status.longName", target = "longName")
    @Mapping(source = "fixture.status.shortName", target = "shortName")
    @Mapping(source = "fixture.status.elapsed", target = "elapsed")
    @Mapping(source = "fixture.venue.venueId", target = "venueId")
    @Mapping(source = "fixture.venue.venueName", target = "venueName")
    @Mapping(source = "fixture.venue.venueCity", target = "venueCity")
    @Mapping(source = "fixture.periods.first", target = "firstPeriod")
    @Mapping(source = "fixture.periods.second", target = "secondPeriod")
    @Mapping(source = "league.id", target = "leagueId")
    @Mapping(source = "league.name", target = "name")
    @Mapping(source = "league.country", target = "country")
    @Mapping(source = "league.logo", target = "logo")
    @Mapping(source = "league.flag", target = "flag")
    @Mapping(source = "league.round", target = "round")
    @Mapping(source = "league.season", target = "season")
    @Mapping(source = "teams.homeTeam.id", target = "homeTeamId")
    @Mapping(source = "teams.homeTeam.name", target = "homeTeamName")
    @Mapping(source = "teams.homeTeam.logo", target = "homeTeamLogo")
    @Mapping(source = "teams.homeTeam.winner", target = "homeTeamWinner")
    @Mapping(source = "teams.awayTeam.id", target = "awayTeamId")
    @Mapping(source = "teams.awayTeam.name", target = "awayTeamName")
    @Mapping(source = "teams.awayTeam.logo", target = "awayTeamLogo")
    @Mapping(source = "teams.awayTeam.winner", target = "awayTeamWinner")
    @Mapping(source = "goals.home", target = "home")
    @Mapping(source = "goals.away", target = "away")
    @Mapping(source = "score.halftime.home", target = "halfTimeHome")
    @Mapping(source = "score.fulltime.away", target = "halfTimeAway")
    @Mapping(source = "score.extratime.home", target = "extraTimeHome")
    @Mapping(source = "score.extratime.away", target = "extraTimeAway")
    @Mapping(source = "score.penalty.home", target = "penaltyHome")
    @Mapping(source = "score.penalty.away", target = "penaltyAway")
    Fixture toEntity(FixtureDto fixtureDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(FixtureDto fixtureDto, @MappingTarget Fixture fixture);

}