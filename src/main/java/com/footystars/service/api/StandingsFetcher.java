package com.footystars.service.api;

import com.footystars.model.api.Standings;
import com.footystars.service.business.LeagueService;
import com.footystars.service.business.StandingsService;
import com.footystars.utils.ParamsProvider;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.footystars.utils.LogsNames.STANDINGS_ERROR;
import static com.footystars.utils.LogsNames.STANDINGS_FETCHED_BY_ID;
import static com.footystars.utils.PathSegment.STANDINGS;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class StandingsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final StandingsService standingsService;
    private final LeagueService leagueService;
    private final ParamsProvider paramsProvider;

    private final Logger log = LoggerFactory.getLogger(StandingsFetcher.class);

    @Async
    public void fetchCurrentSeasonsStandings() {
        getTopLeaguesIds().forEach(this::fetchTopLeaguesCurrentSeasonStandings);
    }

    @Async
    public void fetchTopLeaguesCurrentSeasonStandings(@NotNull Long leagueId) {
        var optionalSeason = leagueService.findCurrentSeasonByLeagueId(leagueId);
        if (optionalSeason.isPresent()) {
            var season = optionalSeason.get();
            fetchStandings(leagueId, season);
        }
    }

    @Async
    public void fetchStandingsByLeagueId(@NotNull Long leagueId) {
        var optionalSeason = leagueService.findCurrentSeasonByLeagueId(leagueId);
        if (optionalSeason.isPresent()) {
            var season = optionalSeason.get();
            fetchStandings(leagueId, season);
        }
        log.info(STANDINGS_FETCHED_BY_ID, leagueId);
    }

    public void fetchStandings(@NotNull Long leagueId, @NotNull Integer season) {
        try {
            var params = paramsProvider.getLeagueAndSeasonParamsMap(leagueId, season);
            var standingsResponse = dataFetcher.fetch(STANDINGS, params, Standings.class).getResponse();
            standingsResponse.parallelStream().forEach(s -> {
                var standings = s.getLeague().getStandings();
                if (standings != null) {
                    standingsService.fetchStandings(standings, params);
                }
            });
        } catch (Exception e) {
            log.error(STANDINGS_ERROR, leagueId, season, e.getMessage());
        }
    }

}