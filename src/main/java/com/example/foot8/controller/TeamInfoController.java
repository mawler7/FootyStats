package com.example.foot8.controller;

import com.example.foot8.api.teams.info.response.TeamInfoResponse;
import com.example.foot8.api.teams.info.response.TeamInfoResponseData;
import com.example.foot8.exception.SaveLeagueException;
import com.example.foot8.exception.SaveTeamException;
import com.example.foot8.service.league.LeagueService;
import com.example.foot8.service.team.TeamResponseService;
import com.example.foot8.utils.League;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamInfoController {

    @Value("${RAPIDAPI_KEY}")
    private String apiKey;

    @Value("${RAPIDAPI_HOST}")
    private String apiHost;

    private final LeagueService leagueService;

    private final TeamResponseService teamResponseService;
    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;

    @GetMapping("/info/{leagueId}/{season}")
    public ResponseEntity<String> getTeamInfoFromResponse(@PathVariable String leagueId, @PathVariable String season) {
        try {
            Response response = executeRequest(createRequestForTeamInfo(leagueId, season));
            extractAndSaveTeamInfoFromResponse(response, leagueId);
            return ResponseEntity.ok("Teams for league: " + leagueId + " and year: " + season + " saved successfully");
        } catch (IOException | SaveTeamException e) {
            throw new SaveTeamException("Failed to get team info from the response.", e);
        }
    }


    @GetMapping("/info/top/{season}")
    public void updateTeamInfoForTopLeagues(@PathVariable String season) {
        Arrays.stream(League.values())
                .parallel()
                .forEach(league -> {
                    try {
                        String id = league.getId();
                        Response response = executeRequest(createRequestForTeamInfo(id, season));
                        extractAndSaveTeamInfoFromResponse(response, id);
                    } catch (IOException | SaveTeamException e) {
                        throw new SaveTeamException("Failed to get team info from the response.", e);
                    }
                });

    }

    @NotNull
    private Response executeRequest(Request request) throws IOException {
        return httpClient.newCall(request).execute();
    }

    @NotNull
    private Request createRequestForTeamInfo(String leagueId, String  season) {
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api-football-v1.p.rapidapi.com")
                .addPathSegments("v3/teams")
                .addQueryParameter("league", leagueId)
                .addQueryParameter("season",season )
                .build();

        return new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", apiHost)
                .build();
    }

    private void extractAndSaveTeamInfoFromResponse(@NotNull Response response, String leagueId) throws IOException, SaveTeamException {
        String jsonData = Objects.requireNonNull(response.body()).string();
        TeamInfoResponse teamInfoResponse = objectMapper.readValue(jsonData, TeamInfoResponse.class);
        List<TeamInfoResponseData> responses = teamInfoResponse.getResponse();

        try {
            responses.forEach(teamInfoResponseData -> saveTeamInfoData(teamInfoResponseData, leagueId));
        } catch (Exception e) {
            throw new SaveLeagueException("Cannot save league: " + e.getMessage(), e);
        }
    }

    private void saveTeamInfoData(TeamInfoResponseData teamInfoResponseData, String leagueId) {
        teamResponseService.saveTeamInfoResponseData(teamInfoResponseData, leagueId);
    }

    @GetMapping("/info")
    public void updateTeamsForAllLeaguesForCurrentSeason() {

        List<Long> allIds = leagueService.findAllIds();
        allIds.forEach(id -> getTeamInfoFromResponse(id.toString(), "2023"));

    }

}


