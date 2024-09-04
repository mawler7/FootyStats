package com.footystars.persistence.mapper;

import com.footystars.model.api.Leagues;
import com.footystars.model.entity.League;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LeagueMapper {

    @Mapping(source = "country.name", target = "country")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    League toEntity(Leagues.LeagueDto leagueDto);

    @Mapping(source = "country.name", target = "country")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    League partialUpdate(Leagues.LeagueDto leagueDto, @MappingTarget League league);

}
