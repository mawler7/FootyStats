package com.footystars.foot8.repository;

import com.footystars.foot8.business.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
//    List<Player> findByTeamsCompetitionsLeagueIdAndTeamsCompetitionsSeasonYear(Long leagueId, Integer season);
}