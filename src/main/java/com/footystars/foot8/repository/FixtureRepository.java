package com.footystars.foot8.repository;


import com.footystars.foot8.business.model.entity.Fixture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface FixtureRepository extends JpaRepository<Fixture, Long> {

    @Query("SELECT f FROM Fixture f WHERE f.season.current is TRUE AND f.season.league.id =: id")
    List<Fixture> findBySeasonLeagueIdAndSeasonCurrent(Long id);

    List<Fixture> findBySeasonLeagueId(Long id);



    @Query("SELECT f FROM Fixture f WHERE f.season.league.id =:id and f.season.year =:year")
    List<Fixture> findBySeasonLeagueIdAndSeasonYear(Long id, Integer year);

    @Query("SELECT f FROM Fixture f WHERE f.date BETWEEN :startDate and :endDate")
    List<Fixture> findByDate(ZonedDateTime startDate, ZonedDateTime endDate);

    @Query("SELECT f FROM Fixture f WHERE f.date BETWEEN :startDate and :endDate" )
//    and f.status = 'NS'")
    List<Fixture> findNotStartedByDate(ZonedDateTime startDate, ZonedDateTime endDate);


}