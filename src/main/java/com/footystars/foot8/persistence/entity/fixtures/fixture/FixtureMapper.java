package com.footystars.foot8.persistence.entity.fixtures.fixture;

import com.footystars.foot8.api.model.fixtures.fixture.LeagueFixture;
import com.footystars.foot8.persistence.entity.club.ClubMapper;
import com.footystars.foot8.persistence.entity.competitions.CompetitionMapper;
import com.footystars.foot8.persistence.entity.fixtures.events.FixtureEventMapper;
import com.footystars.foot8.persistence.entity.fixtures.statistics.FixtureStatMapper;
import com.footystars.foot8.persistence.entity.venues.VenueMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {FixtureEventMapper.class, FixtureStatMapper.class,  CompetitionMapper.class, ClubMapper.class,
                VenueMapper.class})
public interface FixtureMapper {
    Fixture toEntity(FixtureDto fixtureDto);


    @Mapping(source = "fixture.fixtureId", target = "id")
    @Mapping(source = "fixture.date", target = "date")
    @Mapping(source = "fixture.referee", target = "referee")
    @Mapping(source = "fixture.status.elapsed", target = "elapsed")
    @Mapping(source = "fixture.status.fullStatus", target = "fullStatus")
    @Mapping(source = "fixture.status.status", target = "status")
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
    @Mapping(source = "league.country", target = "league.country.name")
    FixtureDto toDto(LeagueFixture leagueFixture);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Fixture partialUpdate(FixtureDto fixtureDto, @MappingTarget Fixture fixture);

    FixtureDto toDto(Fixture fixture);
}