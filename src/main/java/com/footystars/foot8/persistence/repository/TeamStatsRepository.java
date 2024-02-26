package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entities.teams.statistics.TeamStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface TeamStatsRepository extends JpaRepository<TeamStats, Long> {

    @Query("SELECT s FROM TeamStats s WHERE s.teamId = ?1 AND s.leagueId = ?2 AND s.seasonYear = ?3 ORDER BY s.lastUpdated DESC")
    Optional<TeamStats> findByTeamIdAndLeagueAndLeagueSeason(Long teamId, Long leagueId, Long leagueSeason);
}
