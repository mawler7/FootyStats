package com.footystars.foot8.buisness.service;

import com.footystars.foot8.buisness.model.entity.Country;
import com.footystars.foot8.buisness.model.dto.CountryDto;
import com.footystars.foot8.mapper.CountryMapper;
import com.footystars.foot8.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CountryService {

    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    public void fetchCountry(@NotNull CountryDto countryDto) {
        var optionalCountry = findByName(countryDto.getName());
        if (optionalCountry.isEmpty()) {
            var country = countryMapper.toEntity(countryDto);
            save(country);
        } else {
            logger.warn("Country {} }already exists", countryDto.getName());
        }
    }


    public Optional<Country> findByName(String country) {
        return countryRepository.findByName(country);
    }

    public Country save(Country country) {
        return countryRepository.save(country);
    }

}

