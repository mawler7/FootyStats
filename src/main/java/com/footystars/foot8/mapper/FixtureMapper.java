package com.footystars.foot8.mapper;

import com.footystars.foot8.api.model.fixtures.fixture.FixtureApi;
import com.footystars.foot8.business.model.dto.FixtureDto;
import com.footystars.foot8.business.model.dto.FixturePredictionDto;
import com.footystars.foot8.business.model.entity.Fixture;
import com.footystars.foot8.business.model.dto.FixtureDetailsDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {TeamMapper.class, SeasonMapper.class, LineupMapper.class, BetMapper.class, PredictionMapper.class})
public interface FixtureMapper {

    @Mapping(source = "homeTeam.venue.name", target = "homeTeam.venue")
    @Mapping(source = "homeTeam.venue.city", target = "homeTeam.city")
    @Mapping(source = "awayTeam.venue.name", target = "awayTeam.venue")
    @Mapping(source = "awayTeam.venue.city", target = "awayTeam.city")
    Fixture toEntity(FixtureDto fixtureDto);

    @Mapping(source = "fixture.fixtureId", target = "id")
    @Mapping(source = "fixture.date", target = "date")
    @Mapping(source = "fixture.referee", target = "referee")
    @Mapping(source = "fixture.status.elapsed", target = "elapsed")
    @Mapping(source = "fixture.status.fullStatus", target = "fullStatus")
    @Mapping(source = "fixture.status.shortStatus", target = "status")
    @Mapping(source = "fixture.venue.name", target = "venueName")
    @Mapping(source = "fixture.venue.city", target = "city")
    @Mapping(source = "goals.home", target = "home")
    @Mapping(source = "goals.away", target = "away")
    @Mapping(source = "score.halftime.home", target = "homeHT")
    @Mapping(source = "score.halftime.away", target = "awayHT")
    @Mapping(source = "score.fulltime.home", target = "homeFT")
    @Mapping(source = "score.fulltime.away", target = "awayFT")
    @Mapping(source = "score.extratime.home", target = "homeET")
    @Mapping(source = "score.extratime.away", target = "awayET")
    @Mapping(source = "score.penalty.home", target = "homePT")
    @Mapping(source = "score.penalty.away", target = "awayPT")
    @Mapping(source = "league.name", target = "leagueName")
    @Mapping(source = "league.round", target = "round")
    FixtureDto toDto(FixtureApi leagueFixture);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "homeTeam.venue.name", target = "homeTeam.venue")
    @Mapping(source = "homeTeam.venue.city", target = "homeTeam.city")
    @Mapping(source = "awayTeam.venue.name", target = "awayTeam.venue")
    @Mapping(source = "awayTeam.venue.city", target = "awayTeam.city")
    Fixture partialUpdate(FixtureDto fixtureDto, @MappingTarget Fixture fixture);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Fixture partialUpdateDetails(FixtureDetailsDto fixtureDto, @MappingTarget Fixture fixture);

    @Mapping(source = "homeTeam.venue", target = "homeTeam.venue.name")
    @Mapping(source = "homeTeam.city", target = "homeTeam.venue.city")
    @Mapping(source = "awayTeam.venue", target = "awayTeam.venue.name")
    @Mapping(source = "awayTeam.city", target = "awayTeam.venue.city")
    @Mapping(source = "awayTeam.players", target = "awayTeam.players")
    @Mapping(source = "homeTeam.players", target = "homeTeam.players")
    FixtureDto toDto(Fixture fixture);

    @Mapping(source = "homeTeam.venue", target = "homeTeam.venue.name")
    @Mapping(source = "homeTeam.city", target = "homeTeam.venue.city")
    @Mapping(source = "awayTeam.venue", target = "awayTeam.venue.name")
    @Mapping(source = "awayTeam.city", target = "awayTeam.venue.city")
    FixtureDto detailsToDto(FixtureDetailsDto fixtureDto);

    @Mapping(source = "date", target = "date")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "homeTeam.name", target = "home")
    @Mapping(source = "awayTeam.name", target = "away")
    @Mapping(source = "prediction.predictions.goals.homeGoalsPrediction", target = "homePrediction")
    @Mapping(source = "prediction.predictions.goals.awayGoalsPrediction", target = "awayPrediction")
    @Mapping(source = "prediction.predictions.underOver", target = "underOver")
    @Mapping(source = "prediction.predictions.advice", target = "advice")
    FixturePredictionDto toPredictionDto(Fixture fixture);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Fixture partialUpdate(FixtureDetailsDto fixtureDto, @MappingTarget Fixture fixture);

    FixtureDetailsDto toDetailsDto(Fixture fixture);

}