package com.footystars.persistence.mapper;

import com.footystars.model.api.TeamsInfo;
import com.footystars.persistence.entity.Team;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {PlayerMapper.class})
public interface TeamMapper {

    Team toEntity(TeamsInfo.TeamDto teamDto);

    TeamsInfo.TeamDto toDto(Team team);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Team partialUpdate(TeamsInfo.TeamDto teamDto, @MappingTarget Team team);

}