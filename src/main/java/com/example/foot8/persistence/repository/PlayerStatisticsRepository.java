package com.example.foot8.persistence.repository;

import com.example.foot8.persistence.entities.players.statistics.PlayerStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerStatisticsRepository extends JpaRepository<PlayerStatisticsEntity, Long> {
}