package com.footystars.foot8.persistence.entity.leagues;

import com.footystars.foot8.api.model.leagues.league.LeagueApi;
import com.footystars.foot8.persistence.entity.competitions.CompetitionMapper;
import com.footystars.foot8.persistence.entity.countries.CountryMapper;
import com.footystars.foot8.persistence.entity.seasons.SeasonMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {SeasonMapper.class, CompetitionMapper.class, CountryMapper.class})
public interface LeagueMapper {
    League toEntity(LeagueDto leagueDto);

    LeagueDto toDto(League league);

    @Mapping(source = "leagueInfo.name", target = "name")
    @Mapping(source = "leagueInfo.type", target = "type")
    @Mapping(source = "leagueInfo.logo", target = "logo")
    @Mapping(source = "leagueInfo.leagueId", target = "id")
    @Mapping(source = "country", target = "country")
    LeagueDto apiToDto(LeagueApi leagueApi);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    League partialUpdate(LeagueDto leagueDto, @MappingTarget League league);
}