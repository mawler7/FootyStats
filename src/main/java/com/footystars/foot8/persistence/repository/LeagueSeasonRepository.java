package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entities.leagues.seaon.LeagueSeason;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeagueSeasonRepository extends JpaRepository<LeagueSeason, Long> {
    Optional<LeagueSeason> findByLeagueIdAndYear(Long id, int year);
    List<LeagueSeason> findByLeagueId(Long id);

    List<LeagueSeason> findByTeamSeasonsTeamId(Long teamId);
}


