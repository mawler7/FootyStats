package com.footystars.foot8.business.service;

import com.footystars.foot8.api.model.players.player.PlayerApi;
import com.footystars.foot8.business.model.entity.Player;
import com.footystars.foot8.exception.PlayerStatisticsException;
import com.footystars.foot8.mapper.PlayerMapper;
import com.footystars.foot8.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.footystars.foot8.utils.LogsNames.PLAYER_STATS_ERROR;

@Service
@RequiredArgsConstructor
public class PlayerStatsService {

    private final PlayerRepository playerRepository;
    private final SeasonService seasonService;
    private final PlayerMapper playerMapper;

    private final Logger logger = LoggerFactory.getLogger(PlayerStatsService.class);

    @Transactional
    public void updatePlayerStats(@NotNull Player player, @NotNull PlayerApi playerApi) {
        var playerStats = player.getStatistics();
        var playerStatisticApi = playerApi.getStatistics().get(0);

        if (playerStatisticApi != null) {
            var year = playerStatisticApi.getLeague().getYear();
            var leagueId = playerStatisticApi.getLeague().getLeagueId();

            if (year != null && leagueId != null) {
                var current = seasonService.isCurrent(leagueId, year);

                if (current) {
                    var statisticsPlayer = playerStats.stream()
                            .filter(s -> s.getLeague().getLeagueId().equals(leagueId))
                            .filter(s -> s.getLeague().getYear().equals(year))
                            .findFirst();

                    if (statisticsPlayer.isPresent()) {
                        var playerDto = playerMapper.playerApiToDto(playerApi);
                        playerMapper.partialUpdate(playerDto, player);
                        playerRepository.save(player);
                    } else {
                        player.getStatistics().add(playerStatisticApi);
                        playerRepository.save(player);
                    }
                }
            }
        }
    }

    @Transactional
        public void createPlayerStats (@NotNull Player player, @NotNull PlayerApi playerApi){
            try {
                var playerStats = playerApi.getStatistics();
                player.setStatistics(Set.copyOf(playerStats));
                playerRepository.save(player);
            } catch (Exception e) {
                throw new PlayerStatisticsException(PLAYER_STATS_ERROR, e);
            }
        }

    }
