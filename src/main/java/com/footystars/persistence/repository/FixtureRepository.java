package com.footystars.persistence.repository;

import com.footystars.model.entity.Fixture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FixtureRepository extends JpaRepository<Fixture, Long> {

    @Query(value = "SELECT f.id FROM fixtures f WHERE DATE(f.date) >= CURRENT_DATE AND f.date < NOW() + INTERVAL '1 day'", nativeQuery = true)
    List<Long> findTodayFixturesId();

    @Query("SELECT f FROM Fixture  f where f.league.leagueId = :leagueId and f.league.season = :season")
    List<Fixture> findByLeagueIdAndSeason(Long leagueId, Integer season);

    @Query("SELECT f.id FROM Fixture  f where f.league.leagueId = :leagueId and f.league.season = :season")
    List<Long> findIdsByLeagueIdAndSeason(Long leagueId, Integer season);

}