package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.standings.StandingsApi;
import com.footystars.foot8.api.service.params.ParamsProvider;
import com.footystars.foot8.business.model.entity.Season;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.business.service.StandingsService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.footystars.foot8.utils.PathSegment.STANDINGS;
import static com.footystars.foot8.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class StandingsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final StandingsService standingsService;
    private final SeasonService seasonService;
    private final ParamsProvider paramsProvider;

    private final Logger log = LoggerFactory.getLogger(StandingsFetcher.class);

    @Async
    public void fetchCurrentSeasonsStandings() {
        var leaguesIds = getTopLeaguesIds();
        leaguesIds.parallelStream().forEach(this::fetchTopLeaguesCurrentSeasonStandings);
    }

    @Async
    public void fetchTopLeaguesCurrentSeasonStandings(@NotNull Long leagueId) {
        var optionalSeason = seasonService.findCurrentSeasonByLeagueId(leagueId);
        optionalSeason.stream().map(Season::getYear).parallel().forEach(y -> fetchStandings(leagueId, y));
    }

    @Async
    public void fetchStandings(@NotNull Long leagueId, @NotNull Integer season) {
        try {
            var params = paramsProvider.getLeagueAndSeasonParamsMap(leagueId, season);
            var standingApis = dataFetcher.fetch(STANDINGS, params, StandingsApi.class);
            standingsService.fetchStandings(standingApis, params);
        } catch (Exception e) {
            log.error("Error fetching standings for league {} and season {}: {}", leagueId, season, e.getMessage());
        }
    }

    @Async
    public void fetchStandingsByLeagueId(@NotNull Long leagueId) {
        var optionalSeasons = seasonService.findByLeagueId(leagueId);
        optionalSeasons.stream()
                .filter(s -> s.getCurrent().equals(Boolean.TRUE))
                .forEach(season -> {
                    var year = season.getYear();
                    fetchStandings(leagueId, year);
                });
        log.info("Standings fetched by league id: {}", leagueId);
    }

}