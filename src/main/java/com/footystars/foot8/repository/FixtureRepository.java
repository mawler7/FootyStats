package com.footystars.foot8.repository;


import com.footystars.foot8.business.model.entity.Fixture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FixtureRepository extends JpaRepository<Fixture, Long> {
    List<Fixture> findBySeasonLeagueId(Long id);


    List<Fixture> findBySeasonLeagueIdAndSeasonYear(Long id, Integer year);

    List<Fixture> findByDate(String date);
//    boolean existsByCompetitionsLeagueIdAndCompetitionsSeasonYear(Long leagueId, Integer season);
//
//    @Query("SELECT f FROM Fixture f WHERE f.homeTeam.club.id = ?1 OR f.awayTeam.club.id = ?1 order by f.date ASC")
//    List<Fixture> findByCompetitionsByClubId(Long clubId);


}