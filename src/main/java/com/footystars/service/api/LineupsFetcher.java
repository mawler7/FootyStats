package com.footystars.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LineupsFetcher {
//
//    private final ApiDataFetcher dataFetcher;
//    private final LineupsService lineupsService;
//    private final SeasonService seasonService;
//    private final ParamsProvider paramsProvider;
//
//    private final Logger logger = LoggerFactory.getLogger(LineupsFetcher.class);
//
//    public void fetchFavorites() {
//        var allIds = getTopLeaguesIds();
//        allIds.forEach(this::fetchByLeagueId);
//    }
//
//    public void fetchByLeagueId(@NotNull Long leagueId) {
//
//        try {
//            var optionalSeasons = seasonService.findByLeagueId(leagueId);
//
//            optionalSeasons.stream()
//                    .filter(season -> season.getCoverage().getFixtures().isLineups())
//                    .forEach(season -> {
////                fetchBySeason(season);
//
//                logger.info(LINEUPS_LEAGUE_FETCHED, leagueId, season);
//            });
//
//        } catch (Exception e) {
//            logger.error(LINEUP_ERROR, leagueId, e.getMessage());
//        }
//    }
//
//    public void fetchByLeagueIdAndYear(@NotNull Long leagueId, @NotNull Integer year) {
//
//        try {
//            var optionalSeasons = seasonService.findByLeagueIdAndYear(leagueId, year);
//
//            optionalSeasons.stream()
//                    .filter(season -> season.getCoverage().getFixtures().isLineups())
//                    .forEach(season -> {
////                        fetchBySeason(season);
//
//                        logger.info(LINEUPS_LEAGUE_FETCHED, leagueId, season);
//                    });
//
//        } catch (Exception e) {
//            logger.error(LINEUP_ERROR, leagueId, e.getMessage());
//        }
//    }
//
////    public void fetchBySeason(@NotNull Season season) {
////        try {
////            var fixtures = season.getFixtures();
////
////            fixtures.parallelStream().forEach(fixture -> {
////                var fixtureId = fixture.getId();
////                fetchFixtureLineups(fixtureId);
////            });
////        } catch (Exception e) {
////            logger.error(e.getMessage());
////        }
////    }
//
//    public void fetchFixtureLineups(@NotNull Long fixtureId) {
//        var params = paramsProvider.getFixtureParamsMap(fixtureId);
//
//        try {
//            var lineups = dataFetcher.fetch(FIXTURES_LINEUPS, params, LineupsApi.class).getResponse();
//
//            if (!lineups.isEmpty()) {
//                lineupsService.fetchLineups(lineups, fixtureId);
//            }
//        } catch (IOException e) {
//            throw new FixturesLineupException(e, FIXTURE_COULD_NOT_BE_FETCHED + fixtureId);
//        }
//    }
//
//    public void fetchAllSeasonsLineupsByLeagueId(@NotNull Long leagueId) {
//        var seasonYears = seasonService.findByLeagueId(leagueId);
//        seasonYears.parallelStream().forEach(season -> {
//            try {
////                fetchBySeason(season);
//                logger.info(LEAGUE_SEASON_FETCHED, leagueId, season);
//            } catch (Exception e) {
//                throw new FixturesLineupException(e, "Could not find lineups for league: " + leagueId + " in season: " + season);
//            }
//        });
//    }

}