package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.players.statistics.PlayerStatisticApi;
import com.footystars.foot8.buisness.model.entity.Competition;
import com.footystars.foot8.buisness.model.entity.Player;
import com.footystars.foot8.buisness.model.entity.PlayerStats;
import com.footystars.foot8.mapper.PlayerStatsMapper;
import com.footystars.foot8.repository.PlayerStatsRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PlayerStatsService {

    private final PlayerStatsRepository playerStatsRepository;
    private final PlayerService playerService;
    private final PlayerStatsMapper playerStatsMapper;
    private final CompetitionService competitionService;

    private final Logger logger = LoggerFactory.getLogger(PlayerStatsService.class);

    @Transactional
    public void updatePlayerStatistics(Long playerId, PlayerStatisticApi playerStatisticApi, Competition competition) {
        var optionalPlayer = playerService.findById(playerId);
        if (optionalPlayer.isPresent()) {
            var player = optionalPlayer.get();
            var playerStatsList = player.getStatistics();
            if (playerStatsList == null) {
                playerStatsList = new ArrayList<>();
            }
            var existingStatsForCompetition = playerStatsList.stream()
                    .filter(stats -> stats.getCompetition().equals(competition))
                    .findFirst();

            if (existingStatsForCompetition.isPresent()) {
                var playerStats = existingStatsForCompetition.get();
                var playerStatsDto = playerStatsMapper.apiToDto(playerStatisticApi);
                playerStatsMapper.partialUpdate(playerStatsDto, playerStats);
                playerStatsRepository.save(playerStats);
                competitionService.save(competition);
            } else {
                createPlayerStats(playerStatisticApi, competition, player);
            }
        } else {
            logger.warn("Player not found with ID: {}", playerId);
        }
    }


    private void createPlayerStats(@NotNull PlayerStatisticApi playerStatisticApi, @NotNull Competition competition, @NotNull Player player) {
        var playerStatsDto = playerStatsMapper.apiToDto(playerStatisticApi);
        var playerStats = playerStatsMapper.toEntity(playerStatsDto);
        playerStats.setPlayer(player);
        playerStats.setCompetition(competition);
        var savedStats = save(playerStats);
        competition.getPlayerStatistics().add(savedStats);
        competitionService.save(competition);
    }

    public PlayerStats save(PlayerStats player) {
        return playerStatsRepository.save(player);
    }
}
