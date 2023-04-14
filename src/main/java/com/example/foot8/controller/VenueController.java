package com.example.foot8.controller;

import com.example.foot8.buisness.venue.model.VenueDto;
import com.example.foot8.buisness.venue.response.VenueResponse;
import com.example.foot8.exception.VenuesByCountryException;
import com.example.foot8.service.VenueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class VenueController {

    private final VenueService venueService;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    @Value("${X-RapidAPI-Key}")
    private String apiKey;
    @Value("${X-RapidAPI-Host}")
    private String apiHost;

    @GetMapping("/venues/{country}")
    public void getVenuesByCountry(@PathVariable String country) {
        try {
            HttpUrl url = createUrlForVenuesByCountry(country);
            Request request = createRequestWithHeaders(url);
            Response response = executeRequest(request);
            List<VenueDto> venues = extractVenuesFromResponse(response);
            saveVenues(venues);
        } catch (IOException e) {
            throw new VenuesByCountryException("Failed to retrieve venues for country: " + country, e);
        }
    }

    @NotNull
    @Contract("_ -> new")
    private HttpUrl createUrlForVenuesByCountry(String country) {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("api-football-v1.p.rapidapi.com")
                .addPathSegments("v3/venues")
                .addQueryParameter("country", country)
                .build();
    }

    @NotNull
    private Request createRequestWithHeaders(HttpUrl url) {
        return new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", apiHost)
                .build();
    }

    @NotNull
    private Response executeRequest(Request request) throws IOException {
        return httpClient.newCall(request).execute();
    }

    private List<VenueDto> extractVenuesFromResponse(@NotNull Response response) throws IOException {
        String jsonData = Objects.requireNonNull(response.body()).string();
        VenueResponse venueResponse = objectMapper.readValue(jsonData, VenueResponse.class);
        return venueResponse.getResponse();
    }

    private void saveVenues(@NotNull List<VenueDto> venues) {
        venues.forEach(venueService::saveVenue);
    }
}
