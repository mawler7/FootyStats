package com.footystars.foot8.persistence.entities.countries;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    Country toEntity(CountryDto countryDto);
    CountryDto toDto(Country country);

}