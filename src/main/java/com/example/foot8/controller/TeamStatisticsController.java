package com.example.foot8.controller;

import com.example.foot8.api.teams.statistics.model.TeamStatistics;
import com.example.foot8.api.teams.statistics.response.TeamStatisticsResponse;
import com.example.foot8.exception.SaveTeamException;
import com.example.foot8.exception.SaveTeamStatisticsException;
import com.example.foot8.service.team.TeamResponseService;
import com.example.foot8.service.team.TeamService;
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
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/teams/statistics")
@RequiredArgsConstructor
public class TeamStatisticsController {

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final TeamResponseService teamResponseService;
    private final TeamService teamService;

    @Value("${RAPIDAPI_KEY}")
    private String apiKey;

    @Value("${RAPIDAPI_HOST}")
    private String apiHost;


    @GetMapping("/{league}/{season}/{team}")
    public ResponseEntity<String> getTeamStatistics(
            @PathVariable("league") String leagueId,
            @PathVariable("season") String season,
            @PathVariable("team") String teamId) {

        try {
            Response response = executeRequest(createRequestForTeamStatistics(leagueId, season, teamId));
            extractAndSaveTeamStatistics(response);
            return ResponseEntity.ok("Team statistics saved successfully");
        } catch (IOException | SaveTeamException e) {
            throw new SaveTeamException("Failed to get team statistics from the response.", e);
        } catch (SaveTeamStatisticsException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/top/{season}")
    public void updateTeamStatisticsForTopLeaguesInCurrentSeason(@PathVariable("season") String season) {
        Map<String, List<Long>> teamIdsForTopLeagues = teamService.getTeamIdsForTopLeagues();

        teamIdsForTopLeagues.forEach((leagueId, teamIds) -> {
            teamIds.forEach(teamId -> {
                try {
                    Response response = executeRequest(createRequestForTeamStatistics(leagueId, season, String.valueOf(teamId)));
                    extractAndSaveTeamStatistics(response);
                } catch (IOException | SaveTeamException e) {
                    throw new SaveTeamException("Failed to get team statistics from the response.", e);
                } catch (SaveTeamStatisticsException e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }


    @NotNull
    private Response executeRequest(Request request) throws IOException {
        return httpClient.newCall(request).execute();
    }

    @NotNull
    private Request createRequestForTeamStatistics(String leagueId, String season, String teamId) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api-football-v1.p.rapidapi.com")
                .addPathSegments("v3/teams/statistics")
                .addQueryParameter("league", leagueId)
                .addQueryParameter("season", season)
                .addQueryParameter("team", teamId)
                .build();

        return new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", apiHost)
                .build();
    }

    private void extractAndSaveTeamStatistics(@NotNull Response response) throws IOException, SaveTeamStatisticsException {
        String jsonData = Objects.requireNonNull(response.body()).string();
        TeamStatisticsResponse teamStatisticsResponse = objectMapper.readValue(jsonData, TeamStatisticsResponse.class);
        TeamStatistics teamStatistics = teamStatisticsResponse.getResponse();

        try {
            saveTeamStatisticsData(teamStatistics);
        } catch (Exception e) {
            throw new SaveTeamStatisticsException("Cannot save team statistics " + e.getMessage(), e);
        }
    }

    private void saveTeamStatisticsData(@NotNull TeamStatistics responseData) {

        teamResponseService.saveTeamStatsResponseData(responseData);
    }
}
