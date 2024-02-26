package com.footystars.foot8.persistence.entities.leagues;

import com.footystars.foot8.persistence.entities.fixtures.FixtureMapper;
import com.footystars.foot8.persistence.entities.teams.TeamMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {TeamMapper.class, FixtureMapper.class})
public interface LeagueMapper {
    League toEntity(LeagueDto leagueDto);

    LeagueDto toDto(League league);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    League partialUpdate(LeagueDto leagueDto, @MappingTarget League league);
}