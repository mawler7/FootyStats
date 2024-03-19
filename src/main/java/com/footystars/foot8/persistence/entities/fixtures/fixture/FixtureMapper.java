package com.footystars.foot8.persistence.entities.fixtures.fixture;

import com.footystars.foot8.api.model.fixtures.fixture.LeagueFixture;
import com.footystars.foot8.persistence.entities.fixtures.events.FixtureEventMapper;
import com.footystars.foot8.persistence.entities.fixtures.statistics.FixtureStatMapper;
import com.footystars.foot8.persistence.entities.leagues.seaon.LeagueSeasonMapper;
import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeasonMapper;
import com.footystars.foot8.persistence.entities.venues.VenueMapper;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {LeagueSeasonMapper.class, TeamSeasonMapper.class, TeamSeasonMapper.class, FixtureStatMapper.class, FixtureEventMapper.class, VenueMapper.class})
public interface FixtureMapper {
    Fixture toEntity(FixtureDto fixtureDto);

    @AfterMapping
    default void linkStats(@NotNull @MappingTarget Fixture fixture) {
        fixture.getStats().forEach(stat -> stat.setFixture(fixture));
    }

    @AfterMapping
    default void linkEvents(@NotNull @MappingTarget Fixture fixture) {
        fixture.getEvents().forEach(event -> event.setFixture(fixture));
    }

    @Mapping(source = "fixtureInfo.fixtureId", target = "id")
    @Mapping(source = "fixtureInfo.date", target = "date")
    @Mapping(source = "fixtureInfo.referee", target = "referee")
    @Mapping(source = "fixtureInfo.status.elapsed", target = "elapsed")
    @Mapping(source = "fixtureInfo.status.longName", target = "fullStatus")
    @Mapping(source = "fixtureInfo.status.shortName", target = "status")
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
    FixtureDto toDto(LeagueFixture leagueFixture);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Fixture partialUpdate(FixtureDto fixtureDto, @MappingTarget Fixture fixture);
}