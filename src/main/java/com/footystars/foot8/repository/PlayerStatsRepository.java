package com.footystars.foot8.repository;

import com.footystars.foot8.buisness.model.entity.PlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Long> {

}