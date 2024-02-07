package com.example.foot8.persistence.repository;

import com.example.foot8.persistence.entities.players.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerEntityRepository extends JpaRepository<PlayerEntity, Long> {
}