package com.footystars.foot8.persistence.entity.seasons;

import com.footystars.foot8.api.model.leagues.league.season.SeasonApi;
import com.footystars.foot8.persistence.entity.leagues.LeagueMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
uses = {LeagueMapper.class})
public interface SeasonMapper {
    Season toEntity(SeasonDto seasonDto);

    SeasonDto apiToDto(SeasonApi seasonApi);

    SeasonDto toDto(Season season);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Season partialUpdate(SeasonDto seasonDto, @MappingTarget Season season);
}