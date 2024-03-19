package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entities.players.seaon.PlayerSeason;
import com.footystars.foot8.persistence.repository.PlayerSeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlayerSeasonService {

    private final PlayerSeasonRepository playerSeasonRepository;

    @Transactional
    public PlayerSeason save(PlayerSeason playerSeason) {
        return playerSeasonRepository.save(playerSeason);
    }

}
