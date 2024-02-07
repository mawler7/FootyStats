package com.example.foot8.persistence.repository;

import com.example.foot8.persistence.entities.teams.statistics.TeamStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamStatisticsEntityRepository extends JpaRepository<TeamStatisticsEntity, Long> {

    @Query("SELECT s FROM TeamStatisticsEntity s WHERE s.team.id = ?1 AND s.league.leagueId = ?2 AND s.league.leagueSeason = ?3 ORDER BY s.lastUpdated DESC")
    Optional<TeamStatisticsEntity> findByTeamIdAndLeagueAndLeagueSeason(Long teamId, Long leagueId, Long leagueSeason);
}
