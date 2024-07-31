package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.trophies.Trophies;
import com.footystars.foot8.api.service.requester.ParamsProvider;
import com.footystars.foot8.business.service.CoachService;
import com.footystars.foot8.business.service.player.PlayerService;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.business.service.teams.TeamService;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.footystars.foot8.utils.PathSegment.TROPHIES;
import static com.footystars.foot8.utils.TopLeagues.getTopLeaguesIds;


@Service
@RequiredArgsConstructor
public class TrophiesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final PlayerService playerService;
    private final CoachService coachService;
    private final SeasonService seasonService;
    private final ParamsProvider paramsProvider;
    private final TeamService teamService;

    private static final int THREAD_POOL_SIZE = 3;

    private final Logger logger = LoggerFactory.getLogger(TrophiesFetcher.class);

    @Async
    public void fetchFavoritesPlayers() {
        var allIds = getTopLeaguesIds();
        allIds.forEach(this::fetchPlayersTrophiesByLeagueId);
    }

    @Async
    public void fetchFavoritesCoaches() {
        var allIds = getTopLeaguesIds();
        allIds.forEach(this::fetchCoachesTrophiesByLeagueId);
    }

    @Async
    @Transactional
    public void fetchPlayersTrophiesByLeagueId(@NotNull Long leagueId) {
        var seasons = seasonService.findByLeagueId(leagueId);

        if (!seasons.isEmpty()) {
            seasons.forEach(season -> {
                var teams = teamService.getByLeagueIdAndSeason(leagueId, season.getYear());
                teams.forEach(team -> {
                    var players = team.getPlayers();
                    players.forEach(player -> {
                        var playerId = player.getId();
                        var params = paramsProvider.getPlayerParams(playerId);
                        try {
                            var trophiesApis = dataFetcher.fetch(TROPHIES, params, Trophies.class).getResponse();
                            trophiesApis.forEach(trophy -> {
                                var trophies = player.getTrophies();
                                if (trophies != null && !trophies.contains(trophy)) {
                                    trophies.add(trophy);
                                }
                                playerService.save(player);
                            });
                        } catch (Exception e) {
                            logger.error(e.getMessage(), e);
                        }
                    });
                });
            });
        }
    }

    @Async
    @Transactional
    public void fetchCoachesTrophiesByLeagueId(@NotNull Long leagueId) {
        var seasons = seasonService.findByLeagueId(leagueId);

        if (!seasons.isEmpty()) {
            seasons.forEach(season -> {
                var teams = season.getTeams();
                teams.forEach(team -> {
                    var coach = team.getCoach();
                    var coachId = team.getCoach().getId();
                    var params = paramsProvider.getCoachParams(coachId);
                    try {
                        var trophiesApis = dataFetcher.fetch(TROPHIES, params, Trophies.class).getResponse();
                        trophiesApis.forEach(trophy -> {
                            var trophies = coach.getTrophies();
                            if (trophies != null && !trophies.contains(trophy)) {
                                trophies.add(trophy);
                                coachService.save(coach);
                            }
                        });
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                });
            });
        }
    }
}

