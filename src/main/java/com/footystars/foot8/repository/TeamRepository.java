package com.footystars.foot8.repository;

import com.footystars.foot8.buisness.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TeamRepository extends JpaRepository<Team, Long> {


    Optional<Team> findByClubId(Long clubId);

    Optional<Team> findByCompetitionsSeasonCurrentTrueAndClubId(Long clubId);


    Optional<Team> findByClubIdAndCompetitionsLeagueIdAndCompetitionsSeasonYear(Long clubId, Long leagueId, Integer year);
}
