package com.footystars.foot8.repository;

import com.footystars.foot8.business.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Team t WHERE t.clubId = :clubId AND t.season.league.id = :leagueId AND t.season.year = :year")
    List<Team> findBySeasonLeagueIdAndSeasonYear(Long leagueId, Integer year);


    List<Team> findByClubIdAndSeasonCurrent(Long clubId, Boolean current);

    @Query("SELECT t FROM Team t join Season s on s.id = t.season.id WHERE t.clubId = :clubId AND t.season.id = :seasonId")
    Optional<Team> findByClubIdAndSeasonId(Long clubId, Long seasonId);


    List<Team> findBySeasonId(Long seasonId);
}
