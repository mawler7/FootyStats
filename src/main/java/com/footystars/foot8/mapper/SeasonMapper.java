package com.footystars.foot8.mapper;

import com.footystars.foot8.api.model.leagues.league.season.SeasonApi;
import com.footystars.foot8.business.model.dto.SeasonDto;
import com.footystars.foot8.business.model.entity.Season;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SeasonMapper {
    Season toEntity(SeasonDto seasonDto);

    SeasonDto apiToDto(SeasonApi seasonApi);

    SeasonDto toDto(Season season);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Season partialUpdate(SeasonDto seasonDto, @MappingTarget Season season);

    SeasonDto toDto(SeasonApi seasonApi);
}