package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.players.player.PlayerStatistics;
import com.footystars.foot8.api.model.players.statistics.PlayerStatistic;
import com.footystars.foot8.persistence.entities.players.player.Player;
import com.footystars.foot8.persistence.entities.players.player.PlayerDto;
import com.footystars.foot8.persistence.entities.players.player.PlayerMapper;
import com.footystars.foot8.persistence.entities.players.seaon.PlayerSeason;
import com.footystars.foot8.persistence.entities.players.statistics.PlayerStats;
import com.footystars.foot8.persistence.entities.players.statistics.PlayerStatsMapper;
import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeason;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlayerInfoService {

    private final PlayerMapper playerMapper;
    private final PlayerService playerService;
    private final PlayerStatsMapper playerStatsMapper;
    private final TeamSeasonService teamSeasonService;
    private final PlayerSeasonService playerSeasonService;
    private final PlayerStatsService playerStatsService;

    private Logger logger = LoggerFactory.getLogger(PlayerInfoService.class);

    @Transactional
    @Async
    public void fetchPlayers(@NotNull PlayerStatistics playerStatistics) {
        // Extract necessary information
        var statistics = playerStatistics.getStatistics().get(0);
        var playerId = playerStatistics.getPlayerInfo().getId();
        var teamId = statistics.getTeam().getTeamId();
        var leagueId = statistics.getLeague().getLeagueId();
        var season = statistics.getLeague().getSeason();

        // Retrieve team season
        var optionalTeamSeason = teamSeasonService.findByTeamIdLeagueIdAndSeasonYear(teamId, leagueId, season);
        if (optionalTeamSeason.isPresent()) {
            var teamSeason = optionalTeamSeason.get();
            List<PlayerSeason> playerSeasons = teamSeason.getPlayerSeasons();

            // Check if player exists in team season
            if (playerSeasons.stream().noneMatch(playerSeason -> playerSeason.getPlayer().getId().equals(playerId))) {
                // Save new player if not found
                saveNewPlayer(playerId, playerStatistics, teamSeason, statistics);
            } else {
                // Update player statistics if already exists
                updatePlayerStatistics(statistics, playerSeasons);
            }
        }
    }

    private void saveNewPlayer(Long playerId, PlayerStatistics playerStatistics, TeamSeason teamSeason, PlayerStatistic playerStatistic) {
        // Retrieve player
        Optional<Player> optionalPlayer = playerService.findById(playerId);
        if (optionalPlayer.isEmpty()) {
            // Save player
            PlayerDto playerDto = playerMapper.playerInfoToDto(playerStatistics.getPlayerInfo());
            Player playerEntity = playerMapper.toEntity(playerDto);
            Player savedPlayer = playerService.save(playerEntity);

            // Save player statistics
            PlayerStats playerStatsEntity = playerStatsMapper.toEntity(playerStatsMapper.toDto(playerStatistic));
            PlayerStats savedStats = playerStatsService.save(playerStatsEntity);

            // Save player season
            PlayerSeason playerSeason = PlayerSeason.builder()
                    .player(savedPlayer)
                    .playerStats(savedStats)

                    .build();
            PlayerSeason savedPlayerSeason = playerSeasonService.save(playerSeason);
            teamSeason.getPlayerSeasons().add(savedPlayerSeason);

            // Update team season
            teamSeasonService.save(teamSeason);
        }
    }

    private void updatePlayerStatistics(PlayerStatistic statistics, @NotNull List<PlayerSeason> playerSeasons) {
        // Update player statistics
        PlayerStats playerStatsEntity = playerStatsMapper.toEntity(playerStatsMapper.toDto(statistics));
        PlayerStats savedStats = playerStatsService.save(playerStatsEntity);

        // Collect modifications
        List<PlayerSeason> modifiedPlayerSeasons = new ArrayList<>();
        playerSeasons.forEach(playerSeason -> {
            playerSeason.setPlayerStats(savedStats);
            modifiedPlayerSeasons.add(playerSeason);
        });

        // Apply modifications
        modifiedPlayerSeasons.forEach(playerSeasonService::save);
    }



//    @Transactional
//    public void fetchPlayers(@NotNull PlayerStatistics playerStatistics) {
//        var statistics = playerStatistics.getStatistics().get(0);
//        var playerId = playerStatistics.getPlayerInfo().getId();
//        var teamId = statistics.getTeam().getTeamId();
//        var leagueId = statistics.getLeague().getLeagueId();
//        var season = statistics.getLeague().getSeason();
//        var optionalTeamSeason = teamSeasonService.findByTeamIdLeagueIdAndSeasonYear(teamId, leagueId, season);
//
//        if (optionalTeamSeason.isPresent()) {
//            var teamSeason = optionalTeamSeason.get();
//
//            var playerSeasons = teamSeason.getPlayerSeasons();
//
//            if (playerSeasons.stream().noneMatch(playerSeason -> playerSeason.getPlayer().getId().equals(playerId))) {
//                var optionalPlayer = playerService.findById(playerId);
//
//                if (optionalPlayer.isEmpty()) {
//                    var playerInfo = playerStatistics.getPlayerInfo();
//                    var playerDto = playerMapper.playerInfoToDto(playerInfo);
//                    var playerEntity = playerMapper.toEntity(playerDto);
//                    var savedPlayer = playerService.save(playerEntity);
//                    var playerStatistic = playerStatistics.getStatistics().get(0);
//                    var playerStatsDto = playerStatsMapper.toDto(playerStatistic);
//                    var playerStatsEntity = playerStatsMapper.toEntity(playerStatsDto);
//
//                    var savedStats = playerStatsService.save(playerStatsEntity);
//
//                    var playerSeason = PlayerSeason.builder()
//                            .player(savedPlayer)
//                            .playerStats(savedStats)
//                            .teamSeason(teamSeason)
//                            .build();
//                    var savedPlayerSeason = playerSeasonService.save(playerSeason);
//                    playerSeasons.add(savedPlayerSeason);
//
//                    teamSeasonService.save(teamSeason);
//                }
//            } else {
//                var playerStatsDto = playerStatsMapper.toDto(statistics);
//                var playerStats = playerStatsMapper.toEntity(playerStatsDto);
//                PlayerStats savedStats = playerStatsService.save(playerStats);
//                playerSeasons.stream()
//                        .forEach(playerSeason -> {
//                                    playerSeason.setPlayerStats(playerStats);
//                                    playerSeasonService.save(playerSeason);
//
////                                    teamSeasonService.save(teamSeason);
//                                }
//                        );
//            }
//        }
//    }
}


