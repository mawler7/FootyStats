package com.footystars.persistence.repository;

import com.footystars.model.entity.Fixture;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface FixtureRepository extends JpaRepository<Fixture, Long>, JpaSpecificationExecutor<Fixture> {

    @Query(value = "SELECT f.id FROM fixtures f WHERE DATE(f.date) >= CURRENT_DATE AND f.date < NOW() + INTERVAL '1 day'" +
            "AND f.short_status NOT IN ('FT', 'AET', 'PEN', '1H' , '2H', 'ET', 'P', 'HT', 'BT')", nativeQuery = true)
    List<Long> findTodayFixturesId();

    @Query(value = "SELECT f.id FROM fixtures f WHERE DATE(f.date) >= CURRENT_DATE AND f.date < NOW() + INTERVAL '1 day'" +
            "AND f.short_status IN ('1H' , '2H', 'ET', 'P', 'HT', 'BT')", nativeQuery = true)
    List<Long> findTodayLiveFixturesIdToUpdate();

    @Query(value = "SELECT f.id FROM fixtures f WHERE DATE(f.date) >= CURRENT_DATE AND f.date < NOW() + INTERVAL '2 day'" +
            "AND f.short_status NOT IN ('FT', 'AET', 'PEN','1H' , '2H', 'ET', 'P', 'HT', 'BT')", nativeQuery = true)
    List<Long> findTodayFixturesIdToUpdate();

    @Query("SELECT f FROM Fixture  f where f.league.leagueId = :leagueId and f.league.season = :season ")
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

    @Query("SELECT f FROM Fixture f where f.league.leagueId = :leagueId")
    List<Fixture> findByLeagueId(Long leagueId);

    @Query("SELECT f FROM Fixture f WHERE (f.teams.homeTeam.homeId = :teamId OR f.teams.awayTeam.awayId = :teamId) " +
            "AND f.info.status.shortStatus IN ('FT', 'AET', 'PEN') " +
            "ORDER BY f.info.date DESC")
    List<Fixture> findLastMatchesByTeamId(Long teamId, Pageable pageable);

    @Query("SELECT f FROM Fixture f WHERE (f.teams.homeTeam.homeId = :homeId AND f.teams.awayTeam.awayId = :awayId) " +
            "OR (f.teams.homeTeam.homeId = :awayId AND f.teams.awayTeam.awayId = :homeId) " +
            "AND f.info.status.shortStatus IN ('FT', 'AET', 'PEN') " +
            "ORDER BY f.info.date DESC")
    List<Fixture> findHeadToHeadMatches(Long homeId, Long awayId);

    @Query(value = "SELECT f.id  FROM fixtures f WHERE DATE(f.date) <= CURRENT_DATE " +
            "AND f.short_status NOT IN ('FT', 'AET', 'PEN', 'CANC', 'WO', 'Canc', 'AWD', 'TBD')", nativeQuery = true)
    List<Long> findFixturesIdToUpdate();

    @Query(value = "SELECT f.* FROM fixtures f " +
            "JOIN league_seasons l ON l.year = f.season AND l.league_id = f.league_id " +
            "WHERE (f.home_id = :clubId OR f.away_id = :clubId) " +
            "AND l.current = :aTrue", nativeQuery = true)
    List<Fixture> findByClubIdAndSeasonCurrent(Long clubId, Boolean aTrue);


    @Query("SELECT f FROM Fixture f WHERE f.info.date BETWEEN :startDate AND :endDate")
    List<Fixture> findFixturesFromPreviousAndNext7Days(ZonedDateTime startDate, ZonedDateTime endDate);

    @Query(value = """
            SELECT
                logo,
                team,
                COUNT(*) AS first_half_loss_full_match_win,  
                total_matches.total_matches,
                MAX(date) AS last_occurrence   
            FROM
                (
                    SELECT
                        f.home_name AS team,
                        f.home_logo AS logo,
                        f.date
                    FROM fixtures f
                    WHERE f.half_time_home < f.half_time_away
                      AND f.full_time_home > f.full_time_away
            
                    UNION ALL
            
                    SELECT
                        f.away_name AS team,
                        f.away_logo AS logo,
                        f.date
                    FROM fixtures f
                    WHERE f.half_time_away < f.half_time_home
                      AND f.full_time_away > f.full_time_home
                ) AS subquery
            INNER JOIN
                (
                    SELECT
                        team_name,
                        COUNT(*) AS total_matches
                    FROM (
                        SELECT f.home_name AS team_name FROM fixtures f
                        UNION ALL
                        SELECT f.away_name AS team_name FROM fixtures f
                    ) AS all_matches
                    GROUP BY team_name
                ) AS total_matches
            ON subquery.team = total_matches.team_name
            GROUP BY logo, team, total_matches.total_matches
            ORDER BY first_half_loss_full_match_win DESC;
            """, nativeQuery = true)
    List<Object[]> findTeamsWithDrawsAndPercentageAndLastDrawDate();


    @Query(value = """
            select
                logo,
                team,
                result,
                count(*) as result_count,
                total_matches.total_matches
            from
                (
                    select
                        f.home_name as team,
                        f.home_logo as logo,
                        concat(f.full_time_home, ' - ', f.full_time_away) as result
                    from fixtures f
            
                    union all
            
                    select
                        f.away_name as team,
                        f.away_logo as logo,
                        concat(f.full_time_away, ' - ', f.full_time_home) as result
                    from fixtures f
                ) as subquery
                    inner join
                (
                    select
                        team_name,
                        sum(count(*)) over (partition by team_name) as total_matches
                    from (
                             select f.home_name as team_name
                             from fixtures f
                             union all
                             select f.away_name as team_name
                             from fixtures f
                         ) as total_subquery
                    group by team_name
                ) as total_matches
                on subquery.team = total_matches.team_name
            where result != ' - '
            and total_matches > 100
            group by logo, team, result, total_matches.total_matches, logo
            having count(*) >= 25
            order by result desc;
            """, nativeQuery = true)
    List<Object[]> findTeamsMostCorrectScores();


    @Query(value = """
            select
                logo,
                team ,
                count(*) as draws,
                total_matches.total_matches
            from
                (select
                     f.home_name as team,
                     f.home_logo as logo
                 from fixtures f
                 where f.half_time_away = f.half_time_home
            
                 union all
            
                 select
                     f.away_name as team,
                     f.away_logo as logo
                 from fixtures f
                 where f.half_time_away = f.half_time_home
                ) as subquery
            
                    inner join
                (select
                     team_name,
                     sum(count(*)) over (partition by team_name) as total_matches
                 from (
                          select
                              f.home_name as team_name
                          from fixtures f
                          union all
                          select
                              f.away_name as team_name
                          from fixtures f
                      ) as total_subquery
                 group by team_name
                ) as total_matches
                on subquery.team = total_matches.team_name
            
            group by logo, team, total_matches.total_matches
            having count(*) >= 50
            order by draws desc;
            """, nativeQuery = true)
    List<Object[]> findTeamsMostHTDraws();

    @Query("""
                SELECT (
                    f.id,
                    f.info.date,
                    f.teams.homeTeam.homeId,
                    f.teams.homeTeam.homeName,
                    f.teams.homeTeam.homeLogo,
                    f.teams.awayTeam.awayId,
                    f.teams.awayTeam.awayName,
                    f.teams.awayTeam.awayLogo,
                    f.league.leagueName,
                    f.league.logo,
                    f.league.leagueId,
                    f.league.season,
                    f.league.round,
                    f.info.status.elapsed,
                    f.info.status.shortStatus,
                    f.goals.home,
                    f.goals.away,
                    f.score.halftime.halfTimeHome,
                    f.score.halftime.halfTimeAway,
                    f.score.fulltime.fullTimeHome,
                    f.score.fulltime.fullTimeAway,
                    f.score.extratime.extraTimeHome,
                    f.score.extratime.extraTimeAway,
                    f.score.penalty.penaltiesHome,
                    f.score.penalty.penaltiesAway
                )
                FROM Fixture f
                WHERE f.league.leagueId = :leagueId
                  AND f.league.season = :season
                ORDER BY f.info.date ASC
            """)
    List<Object[]> findFixturesByLeagueAndSeason(Long leagueId, Integer season);

    @Query("""
            SELECT
                f.id
                FROM Fixture f
                WHERE f.info.status.shortStatus IN ('NS', 'INT', 'TBD') AND
                     f.info.date <= (CURRENT TIMESTAMP )
            """)
    List<Long> findByStatusNotToDate();

    @Query("SELECT f FROM Fixture f WHERE TO_CHAR(f.info.date, 'YYYY-MM-DD') LIKE :date")
    List<Fixture> findByDate(String date);

}