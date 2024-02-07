package com.example.foot8.controller;

import com.example.foot8.api.venue.model.VenueDto;
import com.example.foot8.api.venue.response.VenueResponse;
import com.example.foot8.exception.SaveVenueException;
import com.example.foot8.service.venue.VenueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueService venueService;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    @Value("${RAPIDAPI_KEY}")
    private String apiKey;

    @Value("${RAPIDAPI_HOST}")
    private String apiHost;

    @GetMapping("/venues/{country}")
    public ResponseEntity<String> getVenuesByCountryName(@PathVariable String country) {
        try {
            Response response = executeRequest(createRequestForVenuesByCountryName(country));
            extractAndSaveFromResponse(response);
            return ResponseEntity.ok("Venues saved successfully");
        } catch (IOException | SaveVenueException e) {

            throw new SaveVenueException("Failed to get venues from the response.", e);
        }
    }
    @GetMapping("/{venueId}")
    public ResponseEntity<String> getVenuesByVenueId(@PathVariable String venueId) {
        try {
            Response response = executeRequest(createRequestForVenuesByVenueId(venueId));
            extractAndSaveFromResponse(response);
            return ResponseEntity.ok("Venues saved successfully");
        } catch (IOException | SaveVenueException e) {
            throw new SaveVenueException("Failed to get venues from the response.", e);
        }
    }

    @NotNull
    private Request createRequestForVenuesByVenueId(String venueId) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api-football-v1.p.rapidapi.com")
                .addPathSegments("v3/venues")
                .addQueryParameter("venueId", venueId)
                .build();

        return new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", apiHost)
                .build();
    }

    @NotNull
    private Request createRequestForVenuesByCountryName(String countryName) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api-football-v1.p.rapidapi.com")
                .addPathSegments("v3/venues")
                .addQueryParameter("countryName", countryName)
                .build();

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

    private void extractAndSaveFromResponse(@NotNull Response response) throws IOException, SaveVenueException {
        String jsonData = Objects.requireNonNull(response.body()).string();
        VenueResponse venueResponse = objectMapper.readValue(jsonData, VenueResponse.class);
        List<VenueDto> venueDtos = venueResponse.getResponse();

        try {
            venueDtos.forEach(this::saveVenueData);
        } catch (Exception e) {
            throw new SaveVenueException("Cannot save venue: " + e.getMessage(), e);
        }
    }

    private void saveVenueData(VenueDto venueDto) {
        venueService.saveOrUpdateVenue(venueDto);
    }
}
