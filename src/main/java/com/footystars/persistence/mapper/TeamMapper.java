package com.footystars.persistence.mapper;

import com.footystars.model.api.Fixtures;
import com.footystars.model.api.TeamsInfo;
import com.footystars.model.entity.Team;
import com.footystars.model.entity.TeamDto;
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

    @Mapping(source = "teamId", target = "id")
    @Mapping(source = "name", target = "info.teamName")
    @Mapping(source = "logo", target = "info.logo")
    Team toEntity(Fixtures.FixtureDto.Statistics.TeamFixture teamFixture);

    TeamsInfo.TeamInfo toEntity(TeamsInfo.TeamInfo teamInfo);


    @Mapping(source = "venue", target = "venue")
    TeamDto toDto(Team team);

}

