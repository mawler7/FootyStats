package com.footystars.service.api;

import com.footystars.model.api.Sidelined;
import com.footystars.model.entity.PlayerSidelined;
import com.footystars.service.business.PlayerService;
import com.footystars.service.business.PlayerSidelinedService;
import com.footystars.utils.ParamsProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.footystars.utils.LogsNames.PLAYERES_SIDELINED_FETCHED;
import static com.footystars.utils.LogsNames.PLAYERES_SIDELINED_FETCHING;
import static com.footystars.utils.PathSegment.SIDELINED;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

/**
 * Service responsible for fetching and storing information about sidelined (injured/suspended) players.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SidelinedFetcher {

    private final ApiDataFetcher dataFetcher;
    private final PlayerService playerService;
    private final ParamsProvider paramsProvider;
    private final PlayerSidelinedService playerSidelinedService;

    /**
     * Fetches sidelined players for all top leagues asynchronously.
     */
    @Async
    public void fetchSidelinedPlayers() {
        getTopLeaguesIds().forEach(this::fetchSidelinedPlayersByLeagueId);
        log.info(PLAYERES_SIDELINED_FETCHED);
    }

    /**
     * Fetches sidelined players for a specific league.
     *
     * @param leagueId The ID of the league.
     */
    public void fetchSidelinedPlayersByLeagueId(@NotNull Long leagueId) {
        var ids = playerService.findPlayerIdsByLeagueId(leagueId);
        log.info(PLAYERES_SIDELINED_FETCHING, ids.size());

        ids.forEach(id -> {
            var params = paramsProvider.getPlayerParams(id);

            try {
                var sidelined = dataFetcher.fetch(SIDELINED, params, Sidelined.class).getResponse();

                if (!sidelined.isEmpty()) {
                    sidelined.parallelStream().forEach(s -> {
                        var started = s.getStarted();
                        var ended = s.getEnded();

                        var optionalPlayerSidelined = playerSidelinedService.findByPlayerIdSeasonAndLeague(id, started, ended);

                        if (optionalPlayerSidelined.isEmpty()) {
                            var playerSidelined = PlayerSidelined.builder()
                                    .playerId(id)
                                    .sidelined(s)
                                    .build();
                            playerSidelinedService.save(playerSidelined);
                        }
                    });
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        });
    }

}