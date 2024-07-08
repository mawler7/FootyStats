package com.footystars.foot8.mapper;

import com.footystars.foot8.api.model.leagues.league.LeagueApi;
import com.footystars.foot8.business.model.entity.League;
import com.footystars.foot8.business.model.dto.LeagueDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LeagueMapper {
    League toEntity(LeagueDto leagueDto);

    LeagueDto toDto(League league);

    @Mapping(source = "leagueInfo.leagueId", target = "id")
    @Mapping(source = "leagueInfo.leagueName", target = "leagueName")
    @Mapping(source = "leagueInfo.type", target = "type")
    @Mapping(source = "leagueInfo.logo", target = "logo")
    @Mapping(source = "country.name", target = "countryName")
    @Mapping(source = "country.countryCode", target = "countryCode")
    @Mapping(source = "country.flag", target = "flag")
    LeagueDto apiToDto(LeagueApi leagueApi);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    League partialUpdate(LeagueDto leagueDto, @MappingTarget League league);
}
