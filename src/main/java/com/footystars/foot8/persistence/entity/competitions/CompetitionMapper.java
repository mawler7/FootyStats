package com.footystars.foot8.persistence.entity.competitions;

import com.footystars.foot8.persistence.entity.club.ClubMapper;
import com.footystars.foot8.persistence.entity.countries.CountryMapper;
import com.footystars.foot8.persistence.entity.fixtures.fixture.FixtureMapper;
import com.footystars.foot8.persistence.entity.leagues.LeagueMapper;
import com.footystars.foot8.persistence.entity.players.player.PlayerMapper;
import com.footystars.foot8.persistence.entity.players.statistics.PlayerStatsMapper;
import com.footystars.foot8.persistence.entity.seasons.SeasonMapper;
import com.footystars.foot8.persistence.entity.teams.statistics.TeamStatsMapper;
import com.footystars.foot8.persistence.entity.teams.team.TeamMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        FixtureMapper.class, TeamStatsMapper.class, PlayerStatsMapper.class, SeasonMapper.class, CountryMapper.class,
        LeagueMapper.class, ClubMapper.class, PlayerMapper.class, TeamMapper.class})
public interface CompetitionMapper {
    Competition toEntity(CompetitionDto competitionDto);

    CompetitionDto toDto(Competition competition);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Competition partialUpdate(CompetitionDto competitionDto, @MappingTarget Competition competition);
}