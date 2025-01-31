package com.footystars.persistence.mapper;

import com.footystars.model.api.Fixtures;
import com.footystars.model.api.TeamsInfo;
import com.footystars.model.entity.Team;
import com.footystars.model.dto.team.TeamDetailsDto;
import com.footystars.model.dto.team.TeamDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {PlayerMapper.class, TeamMapper.class, CoachMapper.class, LeagueMapper.class, TeamStatsMapper.class})
public interface TeamMapper {

    Team toEntity(TeamsInfo.TeamDto teamDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Team partialUpdate(TeamsInfo.TeamDto teamDto, @MappingTarget Team team);

    @Mapping(source = "teamId", target = "info.clubId")
    @Mapping(source = "name", target = "info.teamName")
    @Mapping(source = "logo", target = "info.logo")
    Team toEntity(Fixtures.FixtureDto.Statistics.TeamFixture teamFixture);

    @Mapping(source = "info", target = "info")
    @Mapping(source = "league", target = "league")
    @Mapping(source = "statistics", target = "statistics")
    @Mapping(source = "venue", target = "venue")
    TeamDto toDto(Team team);

    TeamsInfo.TeamInfo toEntity(TeamsInfo.TeamInfo teamInfo);

    @Mapping(source = "league.season", target = "league.season.year")
    Team toEntity(TeamDetailsDto teamDetailsDto);

    TeamDetailsDto toDto1(Team team);

    @Mapping(source = "league.season", target = "league.season.year")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Team partialUpdate(TeamDetailsDto teamDetailsDto, @MappingTarget Team team);
}

