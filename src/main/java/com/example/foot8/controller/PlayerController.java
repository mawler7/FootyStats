package com.example.foot8.controller;

import com.example.foot8.api.players.model.PlayerResponseData;
import com.example.foot8.api.players.response.PlayerResponse;
import com.example.foot8.exception.CurrentSeasonNotFoundException;
import com.example.foot8.exception.PlayerStatisticsException;
import com.example.foot8.service.player.PlayerResponseService;
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
@RequestMapping("/api/players/statistics")
@RequiredArgsConstructor
public class PlayerController {

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final PlayerResponseService playerStatisticsResponseService;

    @Value("${RAPIDAPI_KEY}")
    private String apiKey;

    @Value("${RAPIDAPI_HOST}")
    private String apiHost;

    @GetMapping("/{leagueId}/{season}")
    public ResponseEntity<String> updatePlayerStatisticsByLeagueIdAndSeason(
            @PathVariable("leagueId") String leagueId,
            @PathVariable("season") String season) throws PlayerStatisticsException {

        try {
            var response = executeRequest(createRequestForTeamStatistics(leagueId, season));
            extractAndSavePlayerStatistics(response, leagueId, season);
            return ResponseEntity.ok("Team statistics saved successfully");
        } catch (IOException e) {
            throw new PlayerStatisticsException("Failed to get team statistics from the response.", e);

        }
    }

    @NotNull
    private Response executeRequest(Request request) throws IOException {
        return httpClient.newCall(request).execute();
    }

    private void extractAndSavePlayerStatistics(@NotNull Response response, String id, String season) throws IOException {
        String jsonData = Objects.requireNonNull(response.body()).string();

        PlayerResponse playerStatisticsResponse = objectMapper.readValue(jsonData, PlayerResponse.class);
        List<PlayerResponseData> responseData = playerStatisticsResponse.getResponse();

        responseData.forEach(r -> {
            try {
                savePlayerStatisticsData(r, id, season);
            } catch (CurrentSeasonNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

    }


    public void savePlayerStatisticsData(PlayerResponseData responseData, String leagueId, String season) throws CurrentSeasonNotFoundException {
        playerStatisticsResponseService.handlePlayerResponse(responseData, leagueId, season);
    }

    @NotNull
    private Request createRequestForTeamStatistics(String  leagueId, String leagueSeason) {


        var url = new HttpUrl.Builder()
                .scheme("https")
                .host("api-football-v1.p.rapidapi.com")
                .addPathSegments("v3/players")
                .addQueryParameter("league", leagueId)
                .addQueryParameter("season", leagueSeason)
                .build();

        return new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", apiHost)
                .build();
    }
}
