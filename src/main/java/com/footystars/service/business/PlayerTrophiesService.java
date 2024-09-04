package com.footystars.service.business;

import com.footystars.model.entity.PlayerTrophy;
import com.footystars.persistence.repository.PlayerTrophyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerTrophiesService {

    private final PlayerTrophyRepository playerTrophyRepository;

    public Optional<PlayerTrophy> findByPlayerIdSeasonAndLeague(Long playerId, String  season, String league) {
        return playerTrophyRepository.findByPlayerIdAndTrophy(playerId, season, league);
    }

    @Transactional
    public void save(PlayerTrophy playerTrophy) {
        playerTrophyRepository.save(playerTrophy);
    }

}
