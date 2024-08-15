package com.footystars.foot8.repository;


import com.footystars.foot8.business.model.entity.Fixture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface FixtureRepository extends JpaRepository<Fixture, Long> {


    List<Fixture> findBySeasonLeagueId(Long id);

    @Query("SELECT f FROM Fixture f WHERE f.season.league.id =:id and f.season.year =:year")
    List<Fixture> findBySeasonLeagueIdAndSeasonYear(Long id, Integer year);

    @Query("SELECT f FROM Fixture f WHERE f.date BETWEEN :startDate and :endDate")
    List<Fixture> findByDate(ZonedDateTime startDate, ZonedDateTime endDate);

    @Query("SELECT f FROM Fixture f WHERE f.date BETWEEN :startDate and :endDate" )
//    and f.status = 'NS'")
    List<Fixture> findNotStartedByDate(ZonedDateTime startDate, ZonedDateTime endDate);


    @Query(value = "SELECT f.id FROM fixtures f WHERE DATE(f.date) >= CURRENT_DATE AND f.date < NOW() + INTERVAL '1 day'", nativeQuery = true)
    List<Long> findTodayFixturesId();

    @Query("SELECT f FROM Fixture f "+
            "WHERE FUNCTION('DATE', f.date) = CURRENT_DATE")
    List<Fixture> findTodayFixtures();
    @Query(value = "SELECT f.id FROM fixtures f WHERE DATE(f.date) = CURRENT_DATE AND f.status = 'NS' AND f.date >= NOW() - INTERVAL '10 minutes'", nativeQuery = true)
    List<Long> findTodayMatchesLessThen10minToStart();

    @Query(value = "SELECT 1 FROM fixtures f WHERE DATE(f.date) = CURRENT_DATE AND f.status = 'NS' AND f.date >= NOW() - INTERVAL '10 minutes'", nativeQuery = true)
    boolean existsTodayMatchesLessThan10MinToStart();

    @Query("SELECT f FROM Fixture f " +
            "JOIN FETCH f.homeTeam ht " +
            "JOIN FETCH f.awayTeam at " +
            "LEFT JOIN FETCH ht.statistics htStats " +
            "LEFT JOIN FETCH at.statistics atStats " +
            "LEFT JOIN FETCH f.prediction p " +
            "LEFT JOIN FETCH f.events e " +
            "LEFT JOIN FETCH f.statistics s " +
            "LEFT JOIN FETCH f.lineups l " +
            "LEFT JOIN FETCH l.startXI sx " +
            "LEFT JOIN FETCH sx.statistics ps " +
            "LEFT JOIN FETCH l.substitutes subs " +
            "LEFT JOIN FETCH subs.statistics " +
            "LEFT JOIN FETCH f.bets b " +
            "LEFT JOIN FETCH b.stakes bs " +
            "LEFT JOIN FETCH ht.players hp " +  // Dodano fetch dla graczy w homeTeam
            "LEFT JOIN FETCH at.players ap " +  // Dodano fetch dla graczy w awayTeam
            "WHERE FUNCTION('DATE', f.date) = CURRENT_DATE")
    List<Fixture> findTodayFixturesWithDetails();
}