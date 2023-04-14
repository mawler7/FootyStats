package com.example.foot8.controller;


import com.example.foot8.buisness.match.response.FixtureResponse;
import com.example.foot8.buisness.match.response.MatchDto;
import com.example.foot8.exception.MatchesByLeagueAndSeasonException;
import com.example.foot8.service.MatchService;
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
public class MatchController {

    private final MatchService matchService;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    @Value("${X-RapidAPI-Key}")
    private String apiKey;
    @Value("${X-RapidAPI-Host}")
    private String apiHost;

    @GetMapping("/fixtures/{league}/{season}")
    public void saveMatchesByLeagueAndSeason(@PathVariable String season, @PathVariable String league) {

        try {
            HttpUrl url = createUrlForMatchesByLeagueAndSeason(season, league);
            Request request = createRequestWithHeaders(url);
            Response response = executeRequest(request);
            List<MatchDto> responses = extractMatchesFromResponse(response);
            saveMatches(responses);
        } catch (IOException e) {
            throw new MatchesByLeagueAndSeasonException("Failed to retrieve matches for season " + season + " and league " + league, e);
        }
    }

    @NotNull
    @Contract("_, _ -> _")
    private HttpUrl createUrlForMatchesByLeagueAndSeason(String season, String league) {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("api-football-v1.p.rapidapi.com")
                .addPathSegments("v3/fixtures")
                .addQueryParameter("league", league)
                .addQueryParameter("season", season)
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

    private List<MatchDto> extractMatchesFromResponse(@NotNull Response response) throws  IOException {
        String jsonData = Objects.requireNonNull(response.body()).string();
        FixtureResponse fixtureResponse = objectMapper.readValue(jsonData, FixtureResponse.class);
        return fixtureResponse.getResponse();
    }

    private void saveMatches(@NotNull List<MatchDto> matches) {
        matches.forEach(matchService::saveMatch);
    }

}
