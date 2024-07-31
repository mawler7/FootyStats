package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.sidelined.SidelinedResponse;
import com.footystars.foot8.api.service.requester.ParamsProvider;
import com.footystars.foot8.business.service.player.PlayerService;
import com.footystars.foot8.business.service.SeasonService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static com.footystars.foot8.utils.LogsNames.PLAYERS_SIDELINED;
import static com.footystars.foot8.utils.PathSegment.SIDELINED;
import static com.footystars.foot8.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class SidelinedFetcher {

    private final ApiDataFetcher dataFetcher;
    private final PlayerService playerService;
    private final SeasonService seasonService;
    private final ParamsProvider paramsProvider;

    private final Logger logger = LoggerFactory.getLogger(SidelinedFetcher.class);

    @Async
    public void fetchFavorites() {
        var allIds = getTopLeaguesIds();
        allIds.forEach(this::fetchByLeagueId);
    }
    @Async
    @Transactional
    public void fetchByLeagueId(@NotNull Long leagueId) {
            var optionalSeasons = seasonService.findByLeagueId(leagueId);
            var seasons = optionalSeasons.stream()
                    .filter(season -> season.getCoverage().getFixtures().isLineups())
                    .toList();
            seasons.forEach(season -> fetchByLeagueAndSeason(leagueId, season.getYear()));
    }

    public void fetchByLeagueAndSeason(@NotNull Long leagueId, @NotNull Integer year) {
        var seasons = seasonService.findByLeagueId(leagueId);

        if (!seasons.isEmpty()) {
            seasons.forEach(season -> {
                var teams = season.getTeams();
                teams.forEach(team -> {
                    var players = team.getPlayers();

                    players.forEach(player -> {
                        var playerId = player.getId();
                        var params = paramsProvider.getPlayerParams(playerId);
                        try {
                            var sidelinedApis = dataFetcher.fetch(SIDELINED, params, SidelinedResponse.class).getResponse();
                            sidelinedApis.forEach(sidelined -> {
                                var playerSidelined = player.getSidelined();
                                if (playerSidelined != null && !playerSidelined.contains(sidelined)) {
                                    playerSidelined.add(sidelined);
                                    playerService.save(player);
                                    logger.info(PLAYERS_SIDELINED, playerId, leagueId, year);
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

}