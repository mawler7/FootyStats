package com.footystars.persistence.repository;

import com.footystars.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t.info.clubId FROM Team t WHERE t.league.info.leagueId = :leagueId AND t.league.season.year = :year")
    List<Long> findClubIdsByLeagueIdAndSeasonYear(Long leagueId, Integer year);

    @Query("SELECT t FROM Team t WHERE t.league.info.leagueId = :leagueId AND t.league.season.year = :year AND t.info.clubId = :clubId")
    Optional<Team> findByInfoClubIdAndSeasonYearAndSeasonLeagueLeagueId(Long clubId, Integer year, Long leagueId);

    @Query("SELECT t FROM Team t WHERE  t.league.season.current = :aTrue AND t.info.clubId = :clubId")
    List<Team> findByIdAndLeagueSeasonCurrent(Long clubId, Boolean aTrue);


}

