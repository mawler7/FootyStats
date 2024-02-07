package com.example.foot8.controller;

import com.example.foot8.api.league.response.LeagueResponse;
import com.example.foot8.exception.SaveCountryException;
import com.example.foot8.exception.SaveLeagueException;
import com.example.foot8.service.league.LeagueResponseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequiredArgsConstructor()
@RequestMapping("/api/leagues")
public class LeagueController {

    @Value("${RAPIDAPI_KEY}")
    private String apiKey;

    @Value("${RAPIDAPI_HOST}")
    private String apiHost;

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    private final LeagueResponseService leagueResponseService;

    @GetMapping("/{leagueType}")
    public ResponseEntity<String> getLeaguesFromResponse(@PathVariable String leagueType) {
        try {
            var response = executeRequest(createRequestForLeaguesByType(leagueType));
            extractAndSaveFromResponseAsync(response, leagueType); // Zmiana tutaj
            return ResponseEntity.ok("Leagues saved successfully");
        } catch (IOException | SaveCountryException e) {
            throw new SaveLeagueException("Failed to get leagues from the response.", e);
        }
    }

    @Async
     void extractAndSaveFromResponseAsync(@NotNull Response response, String leagueType) throws IOException, SaveCountryException {
        var jsonData = Objects.requireNonNull(response.body()).string();
        var leagueResponse = objectMapper.readValue(jsonData, LeagueResponse.class);
        var responseDtos = leagueResponse.getResponse();

        try {
            responseDtos.forEach(r -> leagueResponseService.saveResponseDataAsync(r, leagueType));
        } catch (Exception e) {
            throw new SaveLeagueException("Cannot save league: " + e.getMessage(), e);
        }
    }

    @NotNull
    private Response executeRequest(Request request) throws IOException {
        return httpClient.newCall(request).execute();
    }


    @NotNull
    private Request createRequestForLeaguesByType(String leagueType) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api-football-v1.p.rapidapi.com")
                .addPathSegments("v3/leagues")
                .addQueryParameter("type", leagueType)
                .build();

        return new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", apiHost)
                .build();
    }


//    private void saveLeagueData(@NotNull LeagueResponseData leagueResponseData,@NotNull String leagueType) {
//        leagueResponseService.saveResponseDataAsync(leagueResponseData, leagueType);
//    }


}

