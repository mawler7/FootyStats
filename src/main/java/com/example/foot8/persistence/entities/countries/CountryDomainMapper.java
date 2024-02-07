package com.example.foot8.persistence.entities.countries;

import com.example.foot8.api.league.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryDomainMapper {
    @Mapping(source = "code", target = "code")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "flag", target = "flag")
    CountryEntity toEntity(Country country);

    @Mapping(source = "code", target = "code")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "flag", target = "flag")
    Country toDto(CountryEntity countryEntity);

}