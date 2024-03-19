package com.footystars.foot8.persistence.entities.teams.team;

import com.footystars.foot8.api.model.fixtures.fixture.teams.Teams;
import com.footystars.foot8.api.model.teams.info.TeamInfo;
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

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {VenueMapper.class, TeamSeasonMapper.class})
public interface TeamMapper {

    @Mapping(source = "teamInfo.name", target = "name" )
    @Mapping(source = "teamInfo.code", target = "code" )
    @Mapping(source = "teamInfo.founded", target = "founded" )
    @Mapping(source = "teamInfo.national", target = "national" )
    @Mapping(source = "teamInfo.logo", target = "logo" )
    @Mapping(source = "teamInfo.country", target = "country" )
    @Mapping(source = "teamInfo.teamId", target = "id" )
    Team toEntity(TeamDto teamDto);

    TeamDto toDto(Team team);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Team partialUpdate(TeamDto teamDto, @MappingTarget Team team);

}