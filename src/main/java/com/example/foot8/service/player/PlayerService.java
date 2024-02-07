package com.example.foot8.service.player;

import com.example.foot8.persistence.entities.players.PlayerEntity;
import com.example.foot8.persistence.repository.PlayerEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerEntityRepository playerEntityRepository;
    public Optional<PlayerEntity> findById(Long id) {
       return playerEntityRepository.findById(id);
    }

    public void save(PlayerEntity playerEntity) {
        playerEntityRepository.save(playerEntity);
    }
}
