package com.footystars.foot8.api.service.datafetcher;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/players/statistics")
@RequiredArgsConstructor
public class PlayerController {

//    private final OkHttpClient httpClient;
//    private final ObjectMapper objectMapper;
//    private final PlayerResponseService playerStatisticsResponseService;
//
//    @Value("${RAPIDAPI_KEY}")
//    private String apiKey;
//
//    @Value("${RAPIDAPI_HOST}")
//    private String apiHost;
//
//    @GetMapping("/{leagueId}/{season}")
//    public ResponseEntity<String> updatePlayerStatisticsByLeagueIdAndSeason(
//            @PathVariable("leagueId") String leagueId,
//            @PathVariable("season") String season) throws PlayerStatisticsException {
//
//        try {
//            var response = executeRequest(createRequestForTeamStatistics(leagueId, season));
//            extractAndSavePlayerStatistics(response, leagueId, season);
//            return ResponseEntity.ok("Team statistics saved successfully");
//        } catch (IOException e) {
//            throw new PlayerStatisticsException("Failed to get team statistics from the response.", e);
//
//        }
//    }
//
//    @NotNull
//    private Response executeRequest(Request request) throws IOException {
//        return httpClient.newCall(request).execute();
//    }
//
//    private void extractAndSavePlayerStatistics(@NotNull Response response, String id, String season) throws IOException {
//        String jsonData = Objects.requireNonNull(response.body()).string();
//
//        PlayerResponse playerStatisticsResponse = objectMapper.readValue(jsonData, PlayerResponse.class);
//        List<PlayerResponseData> responseData = playerStatisticsResponse.getResponse();
//
//        responseData.forEach(r -> {
//            try {
//                savePlayerStatisticsData(r, id, season);
//            } catch (CurrentSeasonNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        });
//
//    }
//
//
//    public void savePlayerStatisticsData(PlayerResponseData responseData, String leagueId, String season) throws CurrentSeasonNotFoundException {
//        playerStatisticsResponseService.handlePlayerResponse(responseData, leagueId, season);
//    }
//
//    @NotNull
//    private Request createRequestForTeamStatistics(String  leagueId, String leagueSeason) {
//
//
//        var url = new HttpUrl.Builder()
//                .scheme("https")
//                .host("api-football-v1.p.rapidapi.com")
//                .addPathSegments("v3/players")
//                .addQueryParameter("league", leagueId)
//                .addQueryParameter("season", leagueSeason)
//                .build();
//
//        return new Request.Builder()
//                .url(url)
//                .get()
//                .addHeader("X-RapidAPI-Key", apiKey)
//                .addHeader("X-RapidAPI-Host", apiHost)
//                .build();
//    }
}
