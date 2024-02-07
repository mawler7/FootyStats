package com.example.foot8.service.country;

import com.example.foot8.api.league.model.Country;
import com.example.foot8.persistence.entities.countries.CountryDomainMapper;
import com.example.foot8.persistence.entities.countries.CountryEntity;
import com.example.foot8.persistence.repository.CountryEntityRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryEntityRepository countryEntityRepository;
    private final CountryDomainMapper countryDomainMapper;

    public CountryEntity saveOrUpdateCountry(@NotNull Country country) {
        return countryEntityRepository.findByCode(country.getCode())
                .orElseGet(() -> countryEntityRepository.save(countryDomainMapper.toEntity(country)));
    }

    public Optional<CountryEntity> findByName(String country) {
        return countryEntityRepository.findByName(country);
    }

    public CountryEntity save(CountryEntity countryEntity) {
         return countryEntityRepository.save(countryEntity);
    }

}
