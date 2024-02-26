package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entities.players.statistics.PlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Long> {
}