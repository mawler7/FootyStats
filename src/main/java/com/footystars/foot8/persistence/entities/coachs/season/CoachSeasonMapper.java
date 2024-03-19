package com.footystars.foot8.persistence.entities.coachs.season;

import com.footystars.foot8.persistence.entities.coachs.coach.CoachMapper;
import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeasonMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {CoachMapper.class, TeamSeasonMapper.class})
public interface CoachSeasonMapper {
    CoachSeason toEntity(CoachSeasonDto coachSeasonDto);

    CoachSeasonDto toDto(CoachSeason coachSeason);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CoachSeason partialUpdate(CoachSeasonDto coachSeasonDto, @MappingTarget CoachSeason coachSeason);
}