package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.buisness.service.CountryService;
import com.footystars.foot8.buisness.service.VenueService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VenuesFetcher {

    private static final Logger logger = LoggerFactory.getLogger(VenuesFetcher.class);
    private final ApiDataFetcher dataFetcher;
    private final VenueService venueService;
    private final CountryService countryService;


//    @GetMapping
//    public ResponseEntity<String> fetchVenues() {
//        var allCountries = countryService.findAll();
//        return getStringResponseEntity(allCountries);
//    }
//
//    @GetMapping
//    public ResponseEntity<String> fetchVenuesForTopFiveEuropeanLeagues() {
//        var topFiveEuropeanCountries = countryService.getTopFiveEuropeanCountries();
//        return getStringResponseEntity(topFiveEuropeanCountries);
//    }
//
//    @NotNull
//    private ResponseEntity<String> getStringResponseEntity(@NotNull List<Country> topFiveEuropeanCountries) {
//        topFiveEuropeanCountries.forEach(country -> {
//            var params = new HashMap<String, String>();
//            params.put(COUNTRY, country.getName());
//            try {
//                var venues = dataFetcher.fetch(VENUES, params, Venues.class).getVenueList();
//                venues.forEach(venueService::fetchVenue);
//            } catch (IOException e) {
//                logger .error("Error fetching venues", e);
//            }
//        });
//        return ResponseEntity.ok("Venues fetched successfully");
//    }
}