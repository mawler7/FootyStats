package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entities.players.seaon.PlayerSeason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerSeasonRepository extends JpaRepository<PlayerSeason, Long> {
}
