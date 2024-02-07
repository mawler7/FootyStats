package com.example.foot8.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MatchController {

//    private final OkHttpClient httpClient;
//    private final ObjectMapper objectMapper;
//    @Value("${RAPIDAPI_KEY}")
//    private String apiKey;
//    @Value("${RAPIDAPI_HOST}")
//    private String apiHost;
//    private final SeasonService seasonService;
//
//
//
//    @GetMapping("/fixtures/update-all")
////    @Scheduled(cron = "0 0 0 * * *")
//    public void updateAllMatches() {
//        Arrays.stream(League.values())
//                .parallel()
//                .forEach(league -> {
//                    try {
//                        HttpUrl url = createUrlForMatchesByLeagueAndSeason(league.getId());
//                        Request request = createRequestWithHeaders(url);
//                        Response response = executeRequest(request);
//                        List<FixtureDto> responses = extractMatchesFromResponse(response);
//                        if(!responses.isEmpty()) {
////                            saveMatches(responses);
//                        }
//                    } catch (IOException e) {
//                        throw new MatchesByLeagueAndSeasonException("Failed to retrieve matches for season "
////                                + getCurrentSeason()
//                                + " and league " + league.getId(), e);
//                    }
//                });
//    }
//
//    @GetMapping("/fixtures/update")
//    public void saveMatchesByLeagueAndSeason() {
//        Arrays.stream(League.values())
//                .forEach(league->{
//
//
//     try {
//            Request request = new Request.Builder()
//                    .url(createUrlForMatchesByLeagueAndSeason(league.getId()))
//                    .get()
//                    .addHeader("X-RapidAPI-Key", apiKey)
//                    .addHeader("X-RapidAPI-Host", apiHost)
//                    .build();
//
//            Response response = executeRequest(request);
//            List<FixtureDto> responses = extractMatchesFromResponse(response);
////            saveMatches(responses);
//        } catch (IOException e) {
//            throw new MatchesByLeagueAndSeasonException("Failed to retrieve matches for season  and league " + league, e);
//        }});
//    }
//
//
//    @NotNull
//    private HttpUrl createUrlForMatchesByLeagueAndSeason(String leagueId) {
//        return new HttpUrl.Builder()
//                .scheme("https")
//                .host("api-football-v1.p.rapidapi.com")
//                .addPathSegments("v3/fixtures")
//                .addQueryParameter("league", leagueId)
//                .addQueryParameter("season", seasonService.getCurrentSeasonYear().toString())
//                .build();
//    }
//
//    @NotNull
//    private Request createRequestWithHeaders(HttpUrl url) {
//        return new Request.Builder()
//                .url(url)
//                .get()
//                .addHeader("X-RapidAPI-Key", apiKey)
//                .addHeader("X-RapidAPI-Host", apiHost)
//                .build();
//    }
//
//    @NotNull
//    private Response executeRequest(Request request) throws IOException {
//        return httpClient.newCall(request).execute();
//    }
//
//    private List<FixtureDto> extractMatchesFromResponse(@NotNull Response response) throws IOException {
//        String jsonData = Objects.requireNonNull(response.body()).string();
//        FixtureResponse fixtureResponse = objectMapper.readValue(jsonData, FixtureResponse.class);
//        return fixtureResponse.getResponse();
//    }

//    private void saveMatches(@NotNull List<FixtureDto> matches) {
//        matches.forEach(matchService::saveMatch);
//    }

}
