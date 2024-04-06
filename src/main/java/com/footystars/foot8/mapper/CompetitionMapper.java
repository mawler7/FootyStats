package com.footystars.foot8.mapper;

import com.footystars.foot8.buisness.model.entity.Competition;
import com.footystars.foot8.buisness.model.dto.CompetitionDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {
        FixtureMapper.class, TeamStatsMapper.class, PlayerStatsMapper.class, SeasonMapper.class, CountryMapper.class,
        LeagueMapper.class, ClubMapper.class, PlayerMapper.class, TeamMapper.class})
public interface CompetitionMapper {
    Competition toEntity(CompetitionDto competitionDto);

    CompetitionDto toDto(Competition competition);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Competition partialUpdate(CompetitionDto competitionDto, @MappingTarget Competition competition);
}