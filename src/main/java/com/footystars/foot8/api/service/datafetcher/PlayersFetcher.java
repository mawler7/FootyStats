package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.players.PlayersApiResponse;
import com.footystars.foot8.buisness.service.PlayerInfoService;
import com.footystars.foot8.utils.SelectedLeagues;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterName.LEAGUE;
import static com.footystars.foot8.utils.ParameterName.PAGE;
import static com.footystars.foot8.utils.ParameterName.SEASON;
import static com.footystars.foot8.utils.PathSegment.PLAYERS;

@Service
@RequiredArgsConstructor
public class PlayersFetcher {

    private final ApiDataFetcher dataFetcher;
    private final PlayerInfoService playerInfoService;

    private final Logger logger = LoggerFactory.getLogger(PlayersFetcher.class);

    @NotNull
    private static Map<String, String> createParamsMap(Long league, Long season) {
        var params = new HashMap<String, String>();
        params.put(LEAGUE, String.valueOf(league));
        params.put(SEASON, String.valueOf(season));
        return params;
    }

    @Transactional
    public void fetchPlayersStats(@NotNull Long league, @NotNull Long season) {

        var params = createParamsMap(league, season);
        var playersApiResponse = dataFetcher.fetch(PLAYERS, params, PlayersApiResponse.class);
        if (playersApiResponse != null && playersApiResponse.getResponse() != null) {
            var players = playersApiResponse.getResponse();
            var totalPages = playersApiResponse.getPaging().getTotal();
            players.forEach(playerInfoService::fetchPlayers);

            for (int i = 2; i <= totalPages; i++) {
                params.put(PAGE, String.valueOf(i));
                playersApiResponse = dataFetcher.fetch(PLAYERS, params, PlayersApiResponse.class);
                if (playersApiResponse != null && playersApiResponse.getResponse() != null) {
                    playersApiResponse.getResponse().forEach(playerInfoService::fetchPlayers);
                }
            }
        } else {
            logger.warn("No players data found for the given league and season.");
        }
    }

    @Transactional
    public void fetchSelectedLeaguesPlayers() {
        var leagueId = SelectedLeagues.PREMIER_LEAGUE.getId();
        Long seasonYear = 2023L;
        fetchPlayersStats(leagueId, seasonYear);
    }
}
