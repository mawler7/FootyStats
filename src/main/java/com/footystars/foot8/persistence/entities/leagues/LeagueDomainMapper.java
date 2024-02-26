package com.footystars.foot8.persistence.entities.leagues;

import com.footystars.foot8.api.model.league.LeagueResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LeagueDomainMapper {

    @Mapping(source = "leagueInfo.leagueId", target = "leagueId")
    @Mapping(source = "leagueInfo.leagueName", target = "leagueName")
    @Mapping(source = "leagueInfo.leagueType", target = "leagueType")
    @Mapping(source = "leagueInfo.leagueLogo", target = "leagueLogo")
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "country.code", target = "countryCode")
    @Mapping(source = "country.flag", target = "countryFlag")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(LeagueResponse leagueResponse, @MappingTarget LeagueDto league);

    @Mapping(source = "leagueInfo.leagueId", target = "leagueId")
    @Mapping(source = "leagueInfo.leagueName", target = "leagueName")
    @Mapping(source = "leagueInfo.leagueLogo", target = "leagueLogo")
    @Mapping(source = "leagueInfo.leagueType", target = "leagueType")
    @Mapping(source = "country.name", target = "countryName")
    LeagueDto toDto(LeagueResponse leagueResponse);

}
