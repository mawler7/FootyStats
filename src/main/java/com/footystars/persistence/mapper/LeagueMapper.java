package com.footystars.persistence.mapper;

import com.footystars.model.api.Leagues;
import com.footystars.model.dto.league.LeagueDto;
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
    @Mapping(source = "country.countryFlag", target = "info.flag")
    @Mapping(target = "id", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    League toEntity(Leagues.LeagueDto leagueDto);

    @Mapping(source = "country.name", target = "country")
    @Mapping(source = "country.countryFlag", target = "info.flag")
    @Mapping(source = "info.name", target = "info.name")
    @Mapping(source = "info.logo", target = "info.logo")
    @Mapping(source = "info.type", target = "info.type")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    League partialUpdate(Leagues.LeagueDto leagueDto, @MappingTarget League league);

    @Mapping(source = "info.leagueId", target = "id")
    @Mapping(source = "info.name", target = "leagueName")
    @Mapping(source = "info.logo", target = "logo")
    @Mapping(source = "info.type", target = "type")
    @Mapping(source = "info.flag", target = "flag")
    @Mapping(source = "season.year", target = "season")
    @Mapping(source = "season.current", target = "current")
    LeagueDto toDto(League league);

}
