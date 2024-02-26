package com.footystars.foot8.persistence.entities.teams;

import com.footystars.foot8.api.model.teams.info.model.TeamDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TeamMapper {


    Team toEntity(TeamDto teamDto);

    TeamDto toDto(Team team);


}
