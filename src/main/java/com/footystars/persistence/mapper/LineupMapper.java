package com.footystars.persistence.mapper;

import com.footystars.model.entity.Lineup;
import com.footystars.model.dto.fixture.LineupDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LineupMapper {
    Lineup toEntity(LineupDto lineupDto);

    LineupDto toDto(Lineup lineup);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Lineup partialUpdate(LineupDto lineupDto, @MappingTarget Lineup lineup);
}