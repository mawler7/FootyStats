package com.footystars.persistence.mapper;

import com.footystars.model.api.Fixtures;
import com.footystars.model.api.Odds;
import com.footystars.model.dto.fixture.DBViewMatchDto;
import com.footystars.model.dto.fixture.FixtureDto;
import com.footystars.model.dto.fixture.MatchDetailsDto;
import com.footystars.model.dto.fixture.MatchDto;
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

    FixtureDto toDto(Fixture fixture);

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

    @Mapping(target = "type", source = "type")
    @Mapping(target = "value", source = "value")
    FixtureStatistic toStatisticEntity(Fixtures.FixtureDto.Statistics.Statistic.StatisticValue statisticValue);

    List<FixtureStatistic> toStatisticEntities(List<Fixtures.FixtureDto.Statistics.Statistic.StatisticValue> statisticValues);

    @Mapping(target = "team", source = "team")
    FixtureStatistic toFixtureStatistic(Fixtures.FixtureDto.Statistics.Statistic statistic);


    @Mapping(target = "date", source = "info.date")
    @Mapping(target = "venueName", source = "info.venue.venueName")
    @Mapping(target = "referee", source = "info.referee")
    @Mapping(target = "homeTeamId", source = "teams.homeTeam.homeId")
    @Mapping(target = "homeTeamName", source = "teams.homeTeam.homeName")
    @Mapping(target = "homeTeamLogo", source = "teams.homeTeam.homeLogo")
    @Mapping(target = "awayTeamId", source = "teams.awayTeam.awayId")
    @Mapping(target = "awayTeamName", source = "teams.awayTeam.awayName")
    @Mapping(target = "awayTeamLogo", source = "teams.awayTeam.awayLogo")
    @Mapping(target = "leagueName", source = "league.leagueName")
    @Mapping(target = "round", source = "league.round")
    @Mapping(target = "elapsed", source = "info.status.elapsed")
    @Mapping(target = "status", source = "info.status.shortStatus")
    @Mapping(target = "home", source = "goals.home")
    @Mapping(target = "away", source = "goals.away")
    @Mapping(target = "halfTimeHome", source = "score.halftime.halfTimeHome")
    @Mapping(target = "halfTimeAway", source = "score.halftime.halfTimeAway")
    @Mapping(target = "fullTimeHome", source = "score.fulltime.fullTimeHome")
    @Mapping(target = "fullTimeAway", source = "score.fulltime.fullTimeAway")
    @Mapping(target = "extraTimeHome", source = "score.extratime.extraTimeHome")
    @Mapping(target = "extraTimeAway", source = "score.extratime.extraTimeAway")
    @Mapping(target = "penaltiesHome", source = "score.penalty.penaltiesHome")
    @Mapping(target = "penaltiesAway", source = "score.penalty.penaltiesAway")
    @Mapping(target = "homePrediction", source = "prediction.predictions.goals.homePrediction")
    @Mapping(target = "awayPrediction", source = "prediction.predictions.goals.awayPrediction")
    @Mapping(target = "underOver", source = "prediction.predictions.underOver")
    @Mapping(target = "advice", source = "prediction.predictions.advice")
    @Mapping(target = "winnerComment", source = "prediction.predictions.winner.comment")
    @Mapping(target = "winOrDraw", source = "prediction.predictions.winOrDraw")
    @Mapping(target = "homePercentage", source = "prediction.predictions.percent.homePercentage")
    @Mapping(target = "awayPercentage", source = "prediction.predictions.percent.awayPercentage")
    @Mapping(target = "drawPercentage", source = "prediction.predictions.percent.drawPercentage")
    @Mapping(target = "homeForm", source = "prediction.comparison.form.homeForm")
    @Mapping(target = "awayForm", source = "prediction.comparison.form.awayForm")
    @Mapping(target = "homeAtt", source = "prediction.comparison.att.homeAtt")
    @Mapping(target = "awayAtt", source = "prediction.comparison.att.awayAtt")
    @Mapping(target = "homeDef", source = "prediction.comparison.def.homeDef")
    @Mapping(target = "awayDef", source = "prediction.comparison.def.awayDef")
    @Mapping(target = "homePoissonPercentage", source = "prediction.comparison.poissonDistribution.homePercentagePD")
    @Mapping(target = "awayPoissonPercentage", source = "prediction.comparison.poissonDistribution.awayPercentagePD")
    @Mapping(target = "homeH2HPercentage", source = "prediction.comparison.h2h.homePercentageH2H")
    @Mapping(target = "awayH2HPercentage", source = "prediction.comparison.h2h.awayPercentageH2H")
    @Mapping(target = "homeGoalsComparison", source = "prediction.comparison.goals.homeGoalsComparison")
    @Mapping(target = "awayGoalsComparison", source = "prediction.comparison.goals.awayGoalsComparison")
    @Mapping(target = "homeTotalComparison", source = "prediction.comparison.total.homeTotal")
    @Mapping(target = "awayTotalComparison", source = "prediction.comparison.total.awayTotal")
    @Mapping(target= "season", source = "league.season")
    @Mapping(target= "leagueId", source = "league.leagueId")
    @Mapping(target= "leagueLogo", source = "league.logo")
    @Mapping(target = "bets", ignore = true)
    @Mapping(target = "teams", ignore = true)
    @Mapping(target = "players", ignore = true)
    MatchDetailsDto toMatchDetailsDto(Fixture fixture);

    @Mapping(target= "date", source = "info.date")
    @Mapping(target= "homeTeamId", source = "teams.homeTeam.homeId")
    @Mapping(target= "homeTeamName", source = "teams.homeTeam.homeName")
    @Mapping(target= "homeTeamLogo", source = "teams.homeTeam.homeLogo")
    @Mapping(target= "awayTeamId", source = "teams.awayTeam.awayId")
    @Mapping(target= "awayTeamName", source = "teams.awayTeam.awayName")
    @Mapping(target= "awayTeamLogo", source = "teams.awayTeam.awayLogo")
    @Mapping(target= "leagueName", source = "league.leagueName")
    @Mapping(target= "round", source = "league.round")
    @Mapping(target= "elapsed", source = "info.status.elapsed")
    @Mapping(target= "status", source = "info.status.shortStatus")
    @Mapping(target= "home", source = "goals.home")
    @Mapping(target= "away", source = "goals.away")
    @Mapping(target= "homePrediction", source = "prediction.predictions.goals.homePrediction")
    @Mapping(target= "isHomeGoals", source = "prediction.homeGoals")
    @Mapping(target= "awayPrediction", source = "prediction.predictions.goals.awayPrediction")
    @Mapping(target= "isAwayGoals", source = "prediction.awayGoals")
    @Mapping(target= "underOver", source = "prediction.predictions.underOver")
    @Mapping(target= "isOverUnder", source = "prediction.overUnder")
    @Mapping(target= "advice", source = "prediction.predictions.advice")
    @Mapping(target= "isAdvice", source = "prediction.advice")
    @Mapping(target= "leagueId", source = "league.leagueId")
    @Mapping(target= "season", source = "league.season")
    @Mapping(target= "leagueLogo", source = "league.logo")
    @Mapping(target= "halfTimeHome", source = "score.halftime.halfTimeHome")
    @Mapping(target= "halfTimeAway", source = "score.halftime.halfTimeAway")
    @Mapping(target= "fullTimeHome", source = "score.fulltime.fullTimeHome")
    @Mapping(target= "fullTimeAway", source = "score.fulltime.fullTimeAway")
    @Mapping(target= "extraTimeHome", source = "score.extratime.extraTimeHome")
    @Mapping(target= "extraTimeAway", source = "score.extratime.extraTimeAway")
    @Mapping(target= "penaltiesHome", source = "score.penalty.penaltiesHome")
    @Mapping(target= "penaltiesAway", source = "score.penalty.penaltiesAway")
    @Mapping(target = "bets", ignore = true)
    @Mapping(target = "homeForm", ignore = true)
    @Mapping(target = "awayForm", ignore = true)
    MatchDto toMatchDto(Fixture fixture);

    List<MatchDto> toMatchDtoList(List<Fixture> fixtures);

    @Mapping(target= "date", source = "info.date")
    @Mapping(target= "homeTeamName", source = "teams.homeTeam.homeName")
    @Mapping(target= "homeTeamLogo", source = "teams.homeTeam.homeLogo")
    @Mapping(target= "awayTeamName", source = "teams.awayTeam.awayName")
    @Mapping(target= "awayTeamLogo", source = "teams.awayTeam.awayLogo")
    @Mapping(target= "leagueName", source = "league.leagueName")
    @Mapping(target= "status", source = "info.status.shortStatus")
    @Mapping(target= "home", source = "goals.home")
    @Mapping(target= "away", source = "goals.away")
    @Mapping(target= "leagueId", source = "league.leagueId")
    @Mapping(target= "leagueLogo", source = "league.logo")
    @Mapping(target= "halfTimeHome", source = "score.halftime.halfTimeHome")
    @Mapping(target= "halfTimeAway", source = "score.halftime.halfTimeAway")
    @Mapping(target= "fullTimeHome", source = "score.fulltime.fullTimeHome")
    @Mapping(target= "fullTimeAway", source = "score.fulltime.fullTimeAway")
    @Mapping(target= "extraTimeHome", source = "score.extratime.extraTimeHome")
    @Mapping(target= "extraTimeAway", source = "score.extratime.extraTimeAway")
    @Mapping(target= "penaltiesHome", source = "score.penalty.penaltiesHome")
    @Mapping(target= "penaltiesAway", source = "score.penalty.penaltiesAway")
    DBViewMatchDto toDBViewMatchDto(Fixture fixture);
}