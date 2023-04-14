package com.example.foot8.controller;

import com.example.foot8.buisness.venue.response.VenueResponse;
import com.example.foot8.service.VenueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
    public void getMatchesByLeagueAndSeason(@PathVariable String country) throws IOException {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api-football-v1.p.rapidapi.com")
                .addPathSegment("v3")
                .addPathSegment("venues")
                .addQueryParameter("country", country)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", apiHost)
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.body() != null) {
                String jsonData = response.body().string();
                VenueResponse venueResponse = objectMapper.readValue(jsonData, VenueResponse.class);
                venueResponse.getResponse().forEach(venueService::saveVenue);
            }

        }
    }
}
