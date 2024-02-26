package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entities.teams.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Team t WHERE t.league.leagueId = ?1 AND t.season = ?2")
    List<Team> findByLeagueIdAndSeason(Long leagueId, int season);

    @Query("SELECT t.teamId FROM Team t WHERE t.league.leagueId = ?1")
    List<Long> findTeamIdsByLeagueId(Long leagueId);

    @Query("SELECT t FROM Team t WHERE t.teamId =?1 AND t.league.leagueId =?2 AND t.season = ?3")
    Optional<Team> findByTeamIdAndLeagueIdAndSeason(Long teamId, Long leagueId, int season);

    List<Team> findByLeagueIdAndSeason(Long leagueId, Integer season);

    List<Team> findByLeagueId(Long leagueId);

}