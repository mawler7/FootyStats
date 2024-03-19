package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.players.Players;
import com.footystars.foot8.api.model.players.player.PlayerStatistics;
import com.footystars.foot8.buisness.service.LeagueSeasonService;
import com.footystars.foot8.buisness.service.PlayerInfoService;
import com.footystars.foot8.buisness.service.PlayerService;
import com.footystars.foot8.exception.PlayerStatisticsException;
import com.footystars.foot8.exception.TeamInfoException;
import com.footystars.foot8.utils.SelectedLeagues;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.PAGE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;
import static com.footystars.foot8.utils.PathSegment.PLAYERS;

@Service
@RequiredArgsConstructor
public class PlayersFetcher {

    private final ApiDataFetcher dataFetcher;
    private final PlayerInfoService playerInfoService;
    private final LeagueSeasonService leagueSeasonService;

    @NotNull
    private static Map<String, String> createParamsMap(Long league, Long season) {
        var params = new HashMap<String, String>();
        params.put(LEAGUE, String.valueOf(league));
        params.put(SEASON, String.valueOf(season));
        return params;
    }

    @Transactional
    public void fetchPlayersStats(@NotNull Long league, @NotNull Long season) throws TeamInfoException {
        try {
            var params = createParamsMap(league, season);
            var players = dataFetcher.fetch(PLAYERS, params, Players.class);
            var response = dataFetcher.fetch(PLAYERS, params, Players.class).getPlayerList();
            var pages = players.getPaging().getTotal();

            response.forEach(playerInfoService::fetchPlayers);

            for (int i = 2; i <= pages; i++) {
                params.put(PAGE, String.valueOf(i));
                var responses = dataFetcher.fetch(PLAYERS, params, Players.class).getPlayerList();
                responses.forEach(playerInfoService::fetchPlayers);
            }
        } catch (IOException e) {
            throw new TeamInfoException(e, "Failed to fetch team information");
        }
    }

    @Transactional
    public void fetchSelectedLeaguesPlayers() {

        try {
            var id = SelectedLeagues.PREMIER_LEAGUE.getId();
            var year = 2023;
            fetchPlayersStats(id, (long) year);
        } catch (TeamInfoException e) {
            throw new RuntimeException(e);
        }

//        var leagueSeasonsYears = leagueSeasonService.getLeagueSeasonsYears(id);
//        leagueSeasonsYears.forEach(year -> {
//            try {
//                fetchPlayersStats(id, Long.valueOf(year));
//            } catch (Exception e) {
//                throw new PlayerStatisticsException(e, "Could not get team statistics");
//            }
//        });
    }

}
