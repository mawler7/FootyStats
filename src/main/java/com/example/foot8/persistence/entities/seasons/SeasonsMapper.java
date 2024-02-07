package com.example.foot8.persistence.entities.seasons;

import com.example.foot8.api.league.model.Seasons;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeasonsMapper {


    @Mapping(source = "year", target = "year")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "current", target = "current")
    SeasonsEntity toEntity(Seasons seasons);

    @Mapping(source = "year", target = "year")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "current", target = "current")
    Seasons toDto(SeasonsEntity seasonsEntity);

}