package com.footystars.foot8.persistence.entities.leagues.league;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING )
public interface LeagueMapper {

    @Mapping(source = "leagueInfo.id", target = "id")
    @Mapping(source = "leagueInfo.name", target = "name")
    @Mapping(source = "leagueInfo.type", target = "type")
    @Mapping(source = "leagueInfo.logo", target = "logo")
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "country.code", target = "countryCode")
    @Mapping(source = "country.flag", target = "countryFlag")
    League toEntity(LeagueDto leagueDto);



    @Mapping(source = "id", target = "leagueInfo.id")
    @Mapping(source = "name", target = "leagueInfo.name")
    @Mapping(source = "type", target = "leagueInfo.type")
    @Mapping(source = "logo", target = "leagueInfo.logo")
    @Mapping(source = "countryName", target = "country.name")
    @Mapping(source = "countryCode", target = "country.code")
    @Mapping(source = "countryFlag", target = "country.flag")
    LeagueDto toDto(League league);

    @Mapping(source = "leagueInfo.id", target = "id")
    @Mapping(source = "leagueInfo.name", target = "name")
    @Mapping(source = "leagueInfo.type", target = "type")
    @Mapping(source = "leagueInfo.logo", target = "logo")
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "country.code", target = "countryCode")
    @Mapping(source = "country.flag", target = "countryFlag")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    League partialUpdate(LeagueDto leagueDto, @MappingTarget League league);
}