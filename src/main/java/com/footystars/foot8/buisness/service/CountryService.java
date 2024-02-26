package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entities.countries.Country;
import com.footystars.foot8.persistence.entities.countries.CountryDto;
import com.footystars.foot8.persistence.entities.countries.CountryMapper;
import com.footystars.foot8.persistence.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);
    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    public List<Country> getTopFiveEuropeanCountries() {
        return findAll().stream().filter(
                country -> country.getName().equals("England") ||
                        country.getName().equals("France") ||
                        country.getName().equals("Germany") ||
                        country.getName().equals("Italy") ||
                        country.getName().equals("Spain")
        ).toList();
    }

    public void save(@NotNull CountryDto countryDto) {
        var country = countryMapper.toEntity(countryDto);
        countryRepository.save(country);
        logger.info("Country: " + country.getName() + " saved");
    }

    public Optional<Country> findByName(String country) {
        return countryRepository.findByName(country);
    }

}

