package com.footystars.service.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TrophiesFetcher {

//    private final ApiDataFetcher dataFetcher;
//    private final PlayerService playerService;
//    private final CoachService coachService;
//    private final SeasonService seasonService;
//    private final ParamsProvider paramsProvider;
//    private final TeamService teamService;
//
//    private final Logger logger = LoggerFactory.getLogger(TrophiesFetcher.class);
//
//    @Async
//    public void fetchFavoritesPlayers() {
//        var allIds = getTopLeaguesIds();
//        allIds.forEach(this::fetchPlayersTrophiesByLeagueId);
//    }
//
//    @Async
//    public void fetchFavoritesCoaches() {
//        var allIds = getTopLeaguesIds();
//        allIds.forEach(this::fetchCoachesTrophiesByLeagueId);
//    }
//
//    @Async
//    @Transactional
//    public void fetchPlayersTrophiesByLeagueId(@NotNull Long leagueId) {
//        var seasons = seasonService.findByLeagueId(leagueId);

//        if (!seasons.isEmpty()) {
//            seasons.forEach(season -> {
//                var teams = teamService.getClubIdsByLeagueIdAndSeasonYear(leagueId, season.getYear());
//                teams.forEach(team -> {
//                    var players = team.getPlayers();
//                    players.forEach(player -> {
//                        var playerId = player.getId();
//                        var params = paramsProvider.getPlayerParams(playerId);
//                        try {
//                            var trophiesApis = dataFetcher.fetch(TROPHIES, params, Trophies.class).getResponse();
//                            trophiesApis.forEach(trophy -> {
//                                var trophies = player.getTrophies();
//                                if (trophies != null) {
//                                    trophies.add(trophy);
//                                }
//                                playerService.save(player);
//                            });
//                        } catch (Exception e) {
//                            logger.error(e.getMessage(), e);
//                        }
//                    });
//                });
//            });
//        }
//    }
//
//    @Async
//    @Transactional
//    public void fetchCoachesTrophiesByLeagueId(@NotNull Long leagueId) {
//        var seasons = seasonService.findByLeagueId(leagueId);
//
//        if (!seasons.isEmpty()) {
//            seasons.forEach(season -> {
//                var teams = season.getTeams();
//                teams.forEach(team -> {
//                    var coach = team.getCoach();
//                    var coachId = team.getCoach().getId();
//                    var params = paramsProvider.getCoachParams(coachId);
//                    try {
//                        var trophiesApis = dataFetcher.fetch(TROPHIES, params, Trophies.class).getResponse();
//                        trophiesApis.forEach(trophy -> {
//                            var trophies = coach.getTrophies();
//                            if (trophies != null && !trophies.contains(trophy)) {
//                                trophies.add(trophy);
//                                coachService.save(coach);
//                            }
//                        });
//                    } catch (Exception e) {
//                        logger.error(e.getMessage(), e);
//                    }
//                });
//            });
//        }
//    }
}

