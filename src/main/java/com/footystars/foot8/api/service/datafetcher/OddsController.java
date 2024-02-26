package com.footystars.foot8.api.service.datafetcher;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OddsController {

//    private final BookmakerService bookmakerService;
//    @Value("${RAPIDAPI_KEY}")
//    private String apiKey;
//    @Value("${RAPIDAPI_HOST}")
//    private String apiHost;
//    private final OkHttpClient httpClient;
//    private final ObjectMapper objectMapper;
//    private static final String SEASON = "2023";
//
//    @GetMapping("odds/{fixtureId}/{leagueId}")
//    public void getOdds(@PathVariable String fixtureId, @PathVariable String leagueId) {
//
//        try {
//            HttpUrl url = createUrlForOddsByFixtureId(fixtureId, leagueId);
//            Request request = createRequestWithHeaders(url);
//            Response response = executeRequest(request);
//            List<OddsDto> oddsDtos = extractOddsFromResponse(response);
//            oddsDtos.forEach(o ->
//                    saveBookmakers(o.getBookmakersDto(), fixtureId));
//        } catch (IOException e) {
//            throw new MatchesByLeagueAndSeasonException("Failed to retrieve odds for fixture", e);
//        }
//    }
//
//
//    @NotNull
//    @Contract("_, _ -> new")
//    private HttpUrl createUrlForOddsByFixtureId(String fixture, String league) {
//        return new HttpUrl.Builder()
//                .scheme("https")
//                .host("api-football-v1.p.rapidapi.com")
//                .addPathSegments("v3/odds")
//                .addQueryParameter("fixture", fixture)
//                .addQueryParameter("league", league)
//                .addQueryParameter("season", SEASON)
//                .build();
//    }
//
//
//
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
//
//
//    @NotNull
//    private Response executeRequest(Request request) throws IOException {
//        return httpClient.newCall(request).execute();
//    }
//
//    private List<OddsDto> extractOddsFromResponse(@NotNull Response response) throws IOException {
//        String jsonData = Objects.requireNonNull(response.body()).string();
//        OddsResponse oddsResponse = objectMapper.readValue(jsonData, OddsResponse.class);
//        return oddsResponse.getResponse();
//    }
//
//    private void saveBookmakers(@NotNull List<BookmakerDto> bookmakerDtos, String fixtureId) {
//        bookmakerService.saveBookmakers(bookmakerDtos, fixtureId);
//    }
}
