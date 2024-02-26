package com.footystars.foot8.persistence.repository;


import com.footystars.foot8.persistence.entities.leagues.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long> {

    @Query("select l from League l where l.leagueId =?1  and l.seasonYear  =?2 order by l.seasonYear desc ")
    Optional<League> findByLeagueIdAndSeasonYear(Long leagueId, Integer season);
}