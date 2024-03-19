package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TeamSeasonRepository extends JpaRepository<TeamSeason, Long> {
    Optional<TeamSeason> findByTeamIdAndLeagueSeasonLeagueIdAndLeagueSeasonYear(Long teamId, Long leagueId, int seasonYear);

    List<TeamSeason> findByLeagueSeasonLeagueIdAndLeagueSeasonYear(Long id, Integer seasonYear);

    Optional<TeamSeason> findByLeagueSeasonCurrentTrueAndTeamId(Long teamId);


}
