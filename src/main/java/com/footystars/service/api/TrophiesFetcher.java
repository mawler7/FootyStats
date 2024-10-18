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

import static com.footystars.utils.LogsNames.COACHES_TROPHIES_FETCHED;
import static com.footystars.utils.LogsNames.PLAYERES_TROPHIES_FETCHED;
import static com.footystars.utils.LogsNames.PLAYERES_TROPHIES_FETCHING;
import static com.footystars.utils.PathSegment.TROPHIES;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;


@Service
@RequiredArgsConstructor
public class TrophiesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final PlayerService playerService;
    private final PlayerTrophiesService playerTrophiesService;
    private final CoachService coachService;
    private final ParamsProvider paramsProvider;

    private final Logger logger = LoggerFactory.getLogger(TrophiesFetcher.class);

    @Async
    public void fetchPlayersTrophies() {
        getTopLeaguesIds().forEach(this::fetchPlayersTrophiesByLeagueId);
        logger.info(PLAYERES_TROPHIES_FETCHED);
    }


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
                logger.error(e.getMessage(), e);
            }
        });
    }

    @Async
    public void fetchCoachesTrophies() {
        var coaches = coachService.findAll();

        if(!coaches.isEmpty()) {
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
                    logger.error(e.getMessage(), e);
                }
            });
            logger.info(COACHES_TROPHIES_FETCHED);
        }
    }

}


