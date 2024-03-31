package com.footystars.foot8.persistence.entity.coachs;

import com.footystars.foot8.persistence.entity.teams.team.TeamMapper;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {TeamMapper.class})
public interface CoachMapper {
    Coach toEntity(CoachDto coachDto);

    CoachDto toDto(Coach coach);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Coach partialUpdate(CoachDto coachDto, @MappingTarget Coach coach);
}