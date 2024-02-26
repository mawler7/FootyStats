package com.footystars.foot8.api.service;

//import com.footystars.foot8.api.model.players.model.statistics.PlayerStatistics;
//import com.footystars.foot8.buisness.service.PlayerService;
//import com.footystars.foot8.api.model.players.model.player.Player;
//import com.footystars.foot8.exception.CurrentSeasonNotFoundException;
//import com.footystars.foot8.api.model.players.model.PlayerResponseData;
//import com.footystars.foot8.persistence.entities.players.PlayerEntity;
//import com.footystars.foot8.persistence.entities.players.PlayerMapper;
//import com.footystars.foot8.persistence.entities.players.statistics.PlayerStatisticsEntity;
//import com.footystars.foot8.persistence.entities.players.statistics.PlayerStatisticsMapper;
//import com.footystars.foot8.buisness.service.LeagueService;
//import com.footystars.foot8.buisness.service.SeasonService;
//import com.footystars.foot8.buisness.service.TeamInfoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class PlayerResponseService {

//    private final PlayerService playerService;
//    private final PlayerStatisticsService playerStatisticsService;
//    private final PlayerMapper playerMapper;
//    private final SeasonService seasonService;
//    private final TeamInfoService teamService;
//    private final LeagueService leagueService;
//    private final PlayerStatisticsMapper playerStatisticsMapper;
//
//
//    public void handlePlayerResponse(@NotNull PlayerResponseData responseData,
//                                     @NotNull String leagueId,
//                                     @NotNull String season) throws CurrentSeasonNotFoundException {
//
//        var playerFromResponse = getPlayerFromResponse(responseData);
//
//        var id = playerFromResponse.getId();
//
//        if (playerService.findById(id).isEmpty()) {
//            createNewPlayerFromResponse(responseData);
//        }
//
//        var playerStatistics = getStatisticsFromResponse(responseData);
//
//        updatePlayer(id, playerStatistics, leagueId, season);
//    }
//
//    @NotNull
//    private List<PlayerStatisticsEntity> getPlayerStatisticsEntitiesFromResponse(
//            @NotNull List<PlayerStatistics> playerStatistics) {
//        return playerStatistics.stream()
//                .map(playerStatisticsMapper::toEntity)
//                .toList();
//    }
//
//    @NotNull
//    private static List<PlayerStatistics> getStatisticsFromResponse(@NotNull PlayerResponseData responseData) {
//        return responseData.getStatistics();
//    }
//
//    @NotNull
//    private static Player getPlayerFromResponse(@NotNull PlayerResponseData responseData) {
//        return responseData.getPlayer();
//    }
//
//    private void updatePlayer(@NotNull Long id, @NotNull List<PlayerStatistics> playerStatistics,
//                              @NotNull String leagueId, @NotNull String season) throws CurrentSeasonNotFoundException {
//
////        var currentSeason = seasonService.getCurrentSeasonYearForLeague(Long.valueOf(leagueId));
////        var player = playerService.findById(id);
////
////        if (player.isPresent()) {
////            var playerEntity = player.get();
////
////            if (!hasStatisticsForSeasonAndLeague(leagueId, season, playerStatistics)) {
////                updatePlayerStatistics(playerStatistics, playerEntity);
////            } else if (playerStatistics.stream().anyMatch(s-> s.getLeague().getLeagueSeason().equals(currentSeason))) {
////                updatePlayerStatistics(playerStatistics, playerEntity);
////            }
////            }
//        }
//
//
//    private void updatePlayerStatistics(@NotNull List<PlayerStatistics> playerStatistics, PlayerEntity playerEntity) {
//        List<PlayerStatisticsEntity> playerStatisticsEntities = playerStatistics.stream()
//                .map(playerStatisticsMapper::toEntity)
//                .toList();
//        playerStatisticsEntities.forEach(playerStatisticsEntity -> {
//            playerStatisticsEntity.setPlayer(playerEntity);
//            playerStatisticsService.save(playerStatisticsEntity);
//        });
//
//
//
//        playerEntity.setStatistics(playerStatisticsEntities);
//        playerService.save(playerEntity);
//    }
//
//    private boolean hasStatisticsForSeasonAndLeague(String leagueId, String season, List<PlayerStatistics> playerStatistics) {
//        return playerStatistics.stream()
//                .anyMatch(stat -> stat.getLeague().getLeagueId().equals(Long.valueOf(leagueId)) &&
//                        stat.getLeague().getLeagueSeason().equals(Long.valueOf(season)));
//    }
//
//    private void createNewPlayerFromResponse(@NotNull PlayerResponseData responseData) {
//        playerService.save(playerMapper.playerToEntity(responseData.getPlayer()));
//    }
}