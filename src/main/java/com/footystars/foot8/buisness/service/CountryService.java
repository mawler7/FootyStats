package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entity.countries.Country;
import com.footystars.foot8.persistence.entity.countries.CountryDto;
import com.footystars.foot8.persistence.entity.countries.CountryMapper;
import com.footystars.foot8.persistence.repository.CountryRepository;
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
            logger.warn("Country " + countryDto.getName() + "already exists");
        }
    }


    public Optional<Country> findByName(String country) {
        return countryRepository.findByName(country);
    }

    public Country save(Country country) {
        return countryRepository.save(country);
    }

}

