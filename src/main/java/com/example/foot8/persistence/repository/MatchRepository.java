package com.example.foot8.persistence.repository;

import com.example.foot8.persistence.entities.MatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<MatchEntity, Long> {

    Optional<MatchEntity> findByFixtureId(Long id);

    List<MatchEntity> findByLeagueNameAndLeagueSeasonAndStatus(String leagueName, Integer leagueSeason, String status);

    @Query("SELECT MAX(m.leagueSeason) FROM MatchEntity m")
    Integer findMaxLeagueSeason();

    Optional<List<MatchEntity>> findByDateBetween(String start, String end);

    List<MatchEntity> findByHomeTeamIdAndAwayTeamId(Long homeTeamId, Long awayTeamId);

}

