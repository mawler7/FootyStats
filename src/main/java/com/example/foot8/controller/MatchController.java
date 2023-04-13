package com.example.foot8.controller;


import com.example.foot8.buisness.match.response.FixtureResponse;
import com.example.foot8.service.MatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.json.JSONObject;
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
public class MatchController {

    private final MatchService matchService;

    @Value("${X-RapidAPI-Key}")
    private String apiKey;
    @Value("${X-RapidAPI-Host}")
    private String apiHost;

    @GetMapping("/fixtures/{league}/{season}")
    public void getMatchesByLeagueAndSeason(@PathVariable String season, @PathVariable String league) throws IOException {
        Response response = null;
        OkHttpClient client = new OkHttpClient();

        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("api-football-v1.p.rapidapi.com")
                .addPathSegment("v3")
                .addPathSegment("fixtures")
                .addQueryParameter("league", league)
                .addQueryParameter("season", season)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("X-RapidAPI-Key", apiKey)
                .addHeader("X-RapidAPI-Host", apiHost)
                .build();
        try {
            response = client.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonData = response.body().string();
        JSONObject jsonObject = new JSONObject(jsonData);

        String responseJson = jsonObject.toString();

        ObjectMapper objectMapper = new ObjectMapper();
        FixtureResponse fixtureResponse = objectMapper.readValue(responseJson, FixtureResponse.class);

        fixtureResponse.getResponse().forEach(matchService::saveMatch);
    }


}
