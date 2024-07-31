package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.fixtures.statistics.FixtureStatistics;
import com.footystars.foot8.api.service.requester.ParamsProvider;
import com.footystars.foot8.business.service.fixture.FixtureStatisticsService;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.exception.FixtureStatisticsException;
import com.footystars.foot8.utils.LogsNames;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.footystars.foot8.utils.PathSegment.FIXTURES_STATISTICS;
import static com.footystars.foot8.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class FixtureStatisticsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final FixtureStatisticsService fixtureStatisticsService;
    private final ParamsProvider paramsProvider;
    private final SeasonService seasonService;

    private final Logger logger = LoggerFactory.getLogger(FixtureStatisticsFetcher.class);

    private static final int THREAD_POOL_SIZE = 3;


    public void fetchFixtureStats(@NotNull Long fixtureId) {
        var params = paramsProvider.getFixtureParamsMap(fixtureId);
        try {
            var statisticsApi = dataFetcher.fetch(FIXTURES_STATISTICS, params, FixtureStatistics.class).getResponse();
            if (statisticsApi != null) {
                fixtureStatisticsService.fetchFixtureStatistics(statisticsApi, params);
            }
        } catch (IOException e) {
            throw new FixtureStatisticsException(e, "Could not fetch fixture statistics for fixture " + fixtureId);
        }
    }

    public void fetchFavorites() {
        var allIds = getTopLeaguesIds();
        allIds.forEach(this::fetchByLeagueId);
    }

    @Async
    public void fetchByLeagueId(@NotNull Long leagueId) {

        try {
            var optionalSeasons = seasonService.findByLeagueId(leagueId);

            optionalSeasons.stream()
                    .filter(s -> s.getCoverage().getFixtures().isStatisticsFixtures())
                    .forEach(season -> {
                        var fixtures = season.getFixtures();

                        fixtures.stream()
                                .filter(f -> !f.getStatus().equals(LogsNames.FT))
                                .forEach(f -> fetchFixtureStats(f.getId()));
                    });
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void fetchByLeagueAndYear(@NotNull Long leagueId, @NotNull Integer year) {
        var optionalSeason = seasonService.findByLeagueIdAndYear(leagueId, year);
        optionalSeason.stream()
                .filter(s -> s.getCoverage().getFixtures().isStatisticsFixtures())
                .forEach(f -> {
                    var fixtures = f.getFixtures();
                    fixtures.forEach(fixture -> fetchFixtureStats(fixture.getId()));
                });
    }
}

