package com.example.foot8.service.player;

import com.example.foot8.persistence.entities.players.statistics.PlayerStatisticsEntity;
import com.example.foot8.persistence.repository.PlayerStatisticsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerStatisticsService {

    private final PlayerStatisticsRepository playerStatisticsRepository;

    public Optional<PlayerStatisticsEntity> findById(Long id) {
        return playerStatisticsRepository.findById(id);
    }

    public void save(PlayerStatisticsEntity playerStatisticsEntity) {
        playerStatisticsRepository.save(playerStatisticsEntity);
    }

}
