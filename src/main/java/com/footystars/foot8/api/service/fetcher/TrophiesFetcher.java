package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.trophies.TrophiesApi;
import com.footystars.foot8.api.service.requester.ParamsProvider;
import com.footystars.foot8.business.service.CoachService;
import com.footystars.foot8.business.service.PlayerService;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.business.service.TeamService;
import com.footystars.foot8.repository.TeamStatsRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.footystars.foot8.utils.PathSegment.TROPHIES;
import static com.footystars.foot8.utils.SelectedLeagues.getFavoritesLeaguesAndCups;

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
    private final TeamStatsRepository teamStatsRepository;

    @Async
    public void fetchFavoritesPlayers() {
        var allIds = getFavoritesLeaguesAndCups();
        allIds.forEach(this::fetchPlayersTrophiesByLeagueId);
    }

    @Async
    public void fetchFavoritesCoaches() {
        var allIds = getFavoritesLeaguesAndCups();
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
                            var trophiesApis = dataFetcher.fetch(TROPHIES, params, TrophiesApi.class).getResponse();
                            trophiesApis.forEach(trophy -> {
                                var trophies = player.getTrophies();
                                if (trophies != null && !trophies.contains(trophy)) {
                                    trophies.add(trophy);
                                }
                                playerService.save(player);
                            });
                        } catch (IOException e) {
                            throw new RuntimeException(e);
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
                        var trophiesApis = dataFetcher.fetch(TROPHIES, params, TrophiesApi.class).getResponse();
                        trophiesApis.forEach(trophy -> {
                            var trophies = coach.getTrophies();
                            if (trophies != null && !trophies.contains(trophy)) {
                                trophies.add(trophy);
                                coachService.save(coach);
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            });
        }
    }
}

