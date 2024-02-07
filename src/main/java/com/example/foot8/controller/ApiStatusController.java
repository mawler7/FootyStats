package com.example.foot8.controller;

import com.example.foot8.service.team.TeamResponseService;
import com.example.foot8.service.team.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
@RequiredArgsConstructor
public class ApiStatusController {


    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final TeamResponseService teamResponseService;
    private final TeamService teamService;

    @Value("${RAPIDAPI_KEY}")
    private String apiKey;

    @Value("${RAPIDAPI_HOST}")
    private String apiHost;
}
//    get("https://v3.football.api-sports.io/status");