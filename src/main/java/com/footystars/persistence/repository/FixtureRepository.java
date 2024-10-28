package com.footystars.persistence.repository;

import com.footystars.model.entity.Fixture;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface FixtureRepository extends JpaRepository<Fixture, Long> {

    @Query(value = "SELECT f.id FROM fixtures f WHERE DATE(f.date) >= CURRENT_DATE AND f.date < NOW() + INTERVAL '1 day'" +
            "AND f.short_status NOT IN ('FT', 'AET', 'PEN', '1H' , '2H', 'ET', 'P', 'HT', 'BT')", nativeQuery = true)
    List<Long> findTodayFixturesId();

    @Query(value = "SELECT f.id FROM fixtures f WHERE DATE(f.date) >= CURRENT_DATE AND f.date < NOW() + INTERVAL '1 day'" +
            "AND f.short_status IN ('1H' , '2H', 'ET', 'P', 'HT', 'BT')", nativeQuery = true)
    List<Long> findTodayLiveFixturesIdToUpdate();

    @Query(value = "SELECT f.id FROM fixtures f WHERE DATE(f.date) >= CURRENT_DATE AND f.date < NOW() + INTERVAL '2 day'" +
            "AND f.short_status NOT IN ('FT', 'AET', 'PEN','1H' , '2H', 'ET', 'P', 'HT', 'BT')", nativeQuery = true)
    List<Long> findTodayFixturesIdToUpdate();

    @Query("SELECT f FROM Fixture  f where f.league.leagueId = :leagueId and f.league.season = :season " +
            "AND f.info.status.shortStatus IN ('FT', 'AET', 'PEN')")
    List<Fixture> findByLeagueIdAndSeason(Long leagueId, Integer season);

    @Query("SELECT f FROM Fixture f WHERE f.league.leagueId = :leagueId AND f.league.season = :season " +
            "AND f.info.status.shortStatus NOT IN ('FT', 'AET', 'PEN')")
    List<Fixture> findByLeagueIdAndSeasonNotStarted(Long leagueId, Integer season);

    @Query("SELECT f FROM Fixture f WHERE f.league.leagueId = :leagueId AND f.league.season = :season " +
            "AND f.info.status.shortStatus IN ('FT', 'AET', 'PEN')")
    List<Fixture> findByLeagueIdAndSeasonEnded(Long leagueId, Integer season);

    @Query("SELECT f.id FROM Fixture f WHERE f.league.leagueId = :leagueId AND f.league.season = :season")
    List<Long> findIdsByLeagueIdAndSeason(Long leagueId, Integer season);

    @Query(value = "SELECT f.id FROM fixtures f " +
            "JOIN league_seasons l ON l.year = f.season AND l.league_id = f.league_id " +
            "JOIN predictions p ON p.id = f.prediction_id " +
            "WHERE f.home is not null and f.away is not null and l.predictions IS TRUE AND p.advice NOT LIKE 'No predictions available' " +
            "AND p.advice_correct IS NULL", nativeQuery = true)
    List<Long> findUnchecked();

    @Query("SELECT f FROM Fixture f where f.league.leagueId =: leagueId")
    List<Fixture> findByLeagueId(Long leagueId);


    @Query("SELECT f FROM Fixture f WHERE TO_CHAR(f.info.date, 'YYYY-MM-DD') LIKE :date")
    List<Fixture> findByDate(String date);

    @Query("SELECT f FROM Fixture f WHERE (f.teams.homeTeam.homeId = :teamId OR f.teams.awayTeam.awayId = :teamId) " +
            "AND f.info.status.shortStatus IN ('FT', 'AET', 'PEN') " +
            "ORDER BY f.info.date DESC")
    List<Fixture> findLastMatchesByTeamId( Long teamId, Pageable pageable);

    @Query("SELECT f FROM Fixture f WHERE (f.teams.homeTeam.homeId = :homeId AND f.teams.awayTeam.awayId = :awayId) " +
            "OR (f.teams.homeTeam.homeId = :awayId AND f.teams.awayTeam.awayId = :homeId) " +
            "AND f.info.status.shortStatus IN ('FT', 'AET', 'PEN') " +
            "ORDER BY f.info.date DESC")
    List<Fixture> findHeadToHeadMatches( Long homeId,   Long awayId);

    @Query(value = "SELECT f.id  FROM fixtures f WHERE DATE(f.date) <= CURRENT_DATE " +
            "AND f.short_status NOT IN ('FT', 'AET', 'PEN', 'CANC', 'PST', 'WO', 'Canc', 'TBD', 'AWD')", nativeQuery = true)
    List<Long> findFixturesIdToUpdate();

    @Query(value = "SELECT f.* FROM fixtures f " +
            "JOIN league_seasons l ON l.year = f.season AND l.league_id = f.league_id " +
            "WHERE (f.home_id = :clubId OR f.away_id = :clubId) " +
            "AND l.current = :aTrue", nativeQuery = true)
    List<Fixture> findByClubIdAndSeasonCurrent(Long clubId, Boolean aTrue);

    @Query(value = "SELECT f.id FROM fixtures f " +
            "JOIN league_seasons l ON l.year = f.season AND l.league_id = f.league_id " +
            "AND l.current = :aTrue", nativeQuery = true)
    List<Long> findCurrentSeasonFixtures(Boolean aTrue);

    @Query("SELECT f FROM Fixture f WHERE f.info.date BETWEEN :startDate AND :endDate")
    List<Fixture> findFixturesFromPreviousAndNext7Days(ZonedDateTime startDate, ZonedDateTime endDate);

}