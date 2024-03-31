package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.countries.Countries;
import com.footystars.foot8.buisness.service.CountryService;
import com.footystars.foot8.exception.CountryException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.HashMap;

import static com.footystars.foot8.utils.PathSegment.COUNTRIES;


@Service
@RequiredArgsConstructor
public class CountriesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<String> fetchAllCountries() throws CountryException {
        try {
            var params = new HashMap<String, String>();
            var countries = dataFetcher.fetch(COUNTRIES, params, Countries.class).getCountryList();
            countries.forEach(countryService::fetchCountry);
            return ResponseEntity.ok("Countries fetched successfully");
        } catch (IOException e) {
            throw new CountryException(e, e.getMessage());
        }
    }

}