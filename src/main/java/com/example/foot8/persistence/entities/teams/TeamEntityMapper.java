package com.example.foot8.persistence.entities.teams;


import com.example.foot8.api.fixture.model.TeamDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TeamEntityMapper {

    TeamEntityMapper MAPPER = Mappers.getMapper(TeamEntityMapper.class);
    TeamDto toDto(TeamEntity teamEntity);

    TeamEntity toEntity(TeamDto teamDto  );


}