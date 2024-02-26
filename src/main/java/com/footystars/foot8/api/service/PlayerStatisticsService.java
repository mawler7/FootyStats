package com.footystars.foot8.api.service;

import com.footystars.foot8.persistence.entities.players.statistics.PlayerStats;
import com.footystars.foot8.persistence.repository.PlayerStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class
PlayerStatisticsService {

    private final PlayerStatsRepository playerStatisticsRepository;

    public Optional<PlayerStats> findById(Long id) {
        return playerStatisticsRepository.findById(id);
    }

    public void save(PlayerStats playerStatisticsEntity) {
        playerStatisticsRepository.save(playerStatisticsEntity);
    }

}
