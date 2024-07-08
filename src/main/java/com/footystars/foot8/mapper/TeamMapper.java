package com.footystars.foot8.mapper;

import com.footystars.foot8.api.model.teams.TeamApi;
import com.footystars.foot8.business.model.entity.Team;
import com.footystars.foot8.business.model.dto.TeamDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {PlayerMapper.class, TeamStatsMapper.class})
public interface TeamMapper {
    Team toEntity(TeamDto teamDto);

    TeamDto toDto(Team team);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Team partialUpdate(TeamDto teamDto, @MappingTarget Team team);

    @Mapping(source = "teamInfo.clubId", target = "clubId")
    @Mapping(source = "teamInfo.name", target = "name")
    @Mapping(source = "teamInfo.code", target = "code")
    @Mapping(source = "teamInfo.logo", target = "logo")
    @Mapping(source = "teamInfo.founded", target = "founded")
    @Mapping(source = "teamInfo.national", target = "national")
    @Mapping(source = "teamInfo.country", target = "country")
    @Mapping(source = "venue.name", target = "venue")
    @Mapping(source = "venue.address", target = "address")
    @Mapping(source = "venue.city", target = "city")
    @Mapping(source = "venue.capacity", target = "capacity")
    @Mapping(source = "venue.image", target = "image")
    @Mapping(source = "venue.surface", target = "surface")
    TeamDto teamApiToTeamDto(TeamApi teamApi);


}