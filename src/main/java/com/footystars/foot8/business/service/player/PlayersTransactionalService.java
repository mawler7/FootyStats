package com.footystars.foot8.business.service.player;


import com.footystars.foot8.api.model.players.player.PlayerApi;
import com.footystars.foot8.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
public class PlayersTransactionalService {

    private final PlayerService playerService;
    private final PlayerStatsService playerStatsService;
    private final PlayerRepository playerRepository;

    @Transactional
    public void updatePlayerTransactional(@NotNull PlayerApi playerStatistics) {
        var playerId = playerStatistics.getPlayerInfo().getPlayerId();

        if (playerId != null) {
            var optionalPlayer = playerService.findById(playerId);

            if (optionalPlayer.isPresent()) {
                var player = optionalPlayer.get();
                playerStatsService.updatePlayerStats(player, playerStatistics);
            }
        }
    }


    public boolean existsById(Long playerId) {
        return playerRepository.existsById(playerId);
    }

    }
    



