package com.footystars.service.api;

import com.footystars.model.api.Trophies;
import com.footystars.model.entity.PlayerTrophy;
import com.footystars.service.business.CoachService;
import com.footystars.service.business.PlayerService;
import com.footystars.service.business.PlayerTrophiesService;
import com.footystars.utils.ParamsProvider;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import static com.footystars.utils.LogsNames.*;
import static com.footystars.utils.PathSegment.TROPHIES;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

/**
 * Service responsible for fetching trophies won by players and coaches.
 * It retrieves trophies data from an external API and stores it in the database.
 */
@Service
@RequiredArgsConstructor
public class TrophiesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final PlayerService playerService;
    private final PlayerTrophiesService playerTrophiesService;
    private final CoachService coachService;
    private final ParamsProvider paramsProvider;

    private final Logger logger = LoggerFactory.getLogger(TrophiesFetcher.class);

    /**
     * Asynchronously fetches trophies for all players in top leagues.
     */
    @Async
    public void fetchPlayersTrophies() {
        getTopLeaguesIds().forEach(this::fetchPlayersTrophiesByLeagueId);
        logger.info(PLAYERES_TROPHIES_FETCHED);
    }

    /**
     * Fetches player trophies for a given league.
     *
     * @param leagueId ID of the league.
     */
    public void fetchPlayersTrophiesByLeagueId(@NotNull Long leagueId) {
        var ids = playerService.findPlayerIdsByLeagueId(leagueId);
        logger.info(PLAYERES_TROPHIES_FETCHING, ids.size());

        ids.forEach(id -> {
            var params = paramsProvider.getPlayerParams(id);

            try {
                var trophies = dataFetcher.fetch(TROPHIES, params, Trophies.class).getResponse();

                trophies.parallelStream().forEach(t -> {
                    var league = t.getLeague();
                    var season = t.getSeason();
                    var optionalPlayerTrophy = playerTrophiesService.findByPlayerIdSeasonAndLeague(id, season, league);

                    if (optionalPlayerTrophy.isEmpty()) {
                        var playerTrophy = PlayerTrophy.builder()
                                .playerId(id)
                                .trophy(t)
                                .build();
                        playerTrophiesService.save(playerTrophy);
                    }
                });
            } catch (Exception e) {
                logger.error("Error fetching player trophies: {}", e.getMessage(), e);
            }
        });
    }

    /**
     * Asynchronously fetches trophies for all coaches.
     */
    @Async
    public void fetchCoachesTrophies() {
        var coaches = coachService.findAll();

        if (!coaches.isEmpty()) {
            coaches.forEach(c -> {
                var coachId = c.getId();
                var params = paramsProvider.getCoachParams(coachId);

                try {
                    var trophies = dataFetcher.fetch(TROPHIES, params, Trophies.class).getResponse();

                    if (!trophies.isEmpty()) {
                        c.setTrophies(trophies);
                        coachService.save(c);
                    }
                } catch (Exception e) {
                    logger.error("Error fetching coach trophies: {}", e.getMessage(), e);
                }
            });
            logger.info(COACHES_TROPHIES_FETCHED);
        }
    }
}
