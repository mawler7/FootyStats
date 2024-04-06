package com.footystars.foot8.mapper;

import com.footystars.foot8.api.model.teams.info.ClubApi;
import com.footystars.foot8.buisness.model.entity.Club;
import com.footystars.foot8.buisness.model.dto.ClubDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {CountryMapper.class, VenueMapper.class})
public interface ClubMapper {
    Club toEntity(ClubDto clubDto);

    ClubDto toDto(Club club);

    @Mapping(target = "country", ignore = true)
    @Mapping(source = "clubId", target = "id")
    ClubDto apiToDto(ClubApi clubApi);


    @Mapping(target = "country", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Club partialUpdate(ClubDto clubDto, @MappingTarget Club club);
}