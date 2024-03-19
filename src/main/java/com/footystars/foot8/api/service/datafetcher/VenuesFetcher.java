package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.venues.Venues;
import com.footystars.foot8.buisness.service.CountryService;
import com.footystars.foot8.buisness.service.VenueService;
import com.footystars.foot8.persistence.entities.countries.Country;
import com.footystars.foot8.persistence.entities.venues.VenueDto;
import lombok.RequiredArgsConstructor;

import org.jetbrains.annotations.NotNull;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static com.footystars.foot8.utils.ParameterNames.COUNTRY;
import static com.footystars.foot8.utils.PathSegment.VENUES;

import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
public class VenuesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final VenueService venueService;
    private final CountryService countryService;

    private static final Logger logger = LoggerFactory.getLogger(VenuesFetcher.class);


    @GetMapping
    public ResponseEntity<String> fetchVenues() {
        var allCountries = countryService.findAll();
        return getStringResponseEntity(allCountries);
    }

    @GetMapping
    public ResponseEntity<String> fetchVenuesForTopFiveEuropeanLeagues() {
        var topFiveEuropeanCountries = countryService.getTopFiveEuropeanCountries();
        return getStringResponseEntity(topFiveEuropeanCountries);
    }

    @NotNull
    private ResponseEntity<String> getStringResponseEntity(@NotNull List<Country> topFiveEuropeanCountries) {
        topFiveEuropeanCountries.forEach(country -> {
            var params = new HashMap<String, String>();
            params.put(COUNTRY, country.getName());
            try {
                var venues = dataFetcher.fetch(VENUES, params, Venues.class).getVenueList();
                venues.forEach(venueService::fetchVenues);
            } catch (IOException e) {
                logger .error("Error fetching venues", e);
            }
        });
        return ResponseEntity.ok("Venues fetched successfully");
    }
}