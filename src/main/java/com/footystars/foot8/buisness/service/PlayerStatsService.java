package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entities.players.statistics.PlayerStats;
import com.footystars.foot8.persistence.repository.PlayerStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerStatsService {
    private final PlayerStatsRepository playerStatsRepository;

    @Transactional
    public PlayerStats save(PlayerStats player) {
        return playerStatsRepository.save(player);
    }



}
