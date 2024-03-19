package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entities.countries.Country;
import com.footystars.foot8.persistence.entities.countries.CountryDto;
import com.footystars.foot8.persistence.entities.countries.CountryMapper;
import com.footystars.foot8.persistence.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Value("${app.top-five-european-countries}")
    private String topFiveEuropeanCountries;

    private static final Logger logger = LoggerFactory.getLogger(CountryService.class);
    public List<com.footystars.foot8.persistence.entities.countries.Country> findAll() {
        return countryRepository.findAll();
    }

    public List<Country> getTopFiveEuropeanCountries() {
        List<String> countryNames = Arrays.asList(topFiveEuropeanCountries.split(","));
        return findAll().stream()
                .filter(country -> countryNames.contains(country.getName()))
                .toList();
    }

    @Transactional
    public Country save(@NotNull CountryDto countryDto) {
        var country = countryMapper.toEntity(countryDto);
        logger.info("Country: " + country.getName() + " saved");
        return countryRepository.save(country);
    }

    public Optional<Country> findByName(String country) {
        return countryRepository.findByName(country);
    }

}

