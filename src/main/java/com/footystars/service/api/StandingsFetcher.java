package com.footystars.service.api;

import com.footystars.model.api.Standings;
import com.footystars.utils.ParamsProvider;
import com.footystars.service.business.StandingsService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.footystars.utils.PathSegment.STANDINGS;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class StandingsFetcher {

//    private final ApiDataFetcher dataFetcher;
//    private final StandingsService standingsService;
//    private final SeasonService seasonService;
//    private final ParamsProvider paramsProvider;
//
//    private final Logger log = LoggerFactory.getLogger(StandingsFetcher.class);
//
//    @Async
//    public void fetchCurrentSeasonsStandings() {
//        var leaguesIds = getTopLeaguesIds();
//        leaguesIds.parallelStream().forEach(this::fetchTopLeaguesCurrentSeasonStandings);
//    }
//
//    @Async
//    public void fetchTopLeaguesCurrentSeasonStandings(@NotNull Long leagueId) {
//        var optionalSeason = seasonService.findCurrentSeasonByLeagueId(leagueId);
//        optionalSeason.stream().map(Season::getYear).parallel().forEach(y -> fetchStandings(leagueId, y));
//    }
//
//    public void fetchStandings(@NotNull Long leagueId, @NotNull Integer season) {
//        try {
//            var params = paramsProvider.getLeagueAndSeasonParamsMap(leagueId, season);
//            var standingApis = dataFetcher.fetch(STANDINGS, params, Standings.class);
//            standingsService.fetchStandings(standingApis, params);
//        } catch (Exception e) {
//            log.error("Error fetching standings for league {} and season {}: {}", leagueId, season, e.getMessage());
//        }
//    }
//
//    @Async
//    public void fetchStandingsByLeagueId(@NotNull Long leagueId) {
//        var optionalSeasons = seasonService.findByLeagueId(leagueId);
//        optionalSeasons.stream()
//                .filter(s -> s.getCurrent().equals(Boolean.TRUE))
//                .forEach(season -> {
//                    var year = season.getYear();
//                    fetchStandings(leagueId, year);
//                });
//        log.info("Standings fetched by league id: {}", leagueId);
//    }

}