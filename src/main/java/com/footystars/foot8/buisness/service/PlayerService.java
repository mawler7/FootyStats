package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entities.players.Player;
import com.footystars.foot8.persistence.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerEntityRepository;

    public Optional<Player> findById(Long id) {
        return playerEntityRepository.findById(id);
    }

    public void save(Player playerEntity) {
        playerEntityRepository.save(playerEntity);
    }
}
