package com.footystars.service.business;

import com.footystars.model.entity.PlayerSidelined;
import com.footystars.persistence.repository.PlayerSidelinedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerSidelinedService {

    private final PlayerSidelinedRepository playerSidelinedRepository;

    public Optional<PlayerSidelined> findByPlayerIdSeasonAndLeague(Long playerId, String started, String ended) {
        return playerSidelinedRepository.findByPlayerIdAndSidelinedDates(playerId, started, ended);
    }

    @Transactional
    public void save(PlayerSidelined playerSidelined) {
        playerSidelinedRepository.save(playerSidelined);
    }

}
