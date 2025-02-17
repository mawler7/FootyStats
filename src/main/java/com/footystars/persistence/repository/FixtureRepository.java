package com.footystars.persistence.repository;

import com.footystars.model.api.TeamStatistics;
import com.footystars.model.dto.fixture.ClubMatchDto;
import com.footystars.model.dto.fixture.LeaguePredictionsDto;
import com.footystars.model.dto.fixture.MatchDto;
import com.footystars.model.dto.fixture.MatchInfo;
import com.footystars.model.dto.fixture.PredictionStats;
import com.footystars.model.dto.fixture.TeamPredictionStats;
import com.footystars.model.entity.Fixture;
import com.footystars.model.entity.FixturePlayer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public interface FixtureRepository extends JpaRepository<Fixture, Long>, JpaSpecificationExecutor<Fixture> {

    @Query("SELECT f FROM Fixture f JOIN FETCH f.league WHERE f.id = :id")
    Optional<Fixture> findByIdWithLeague(@Param("id") Long id);

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
            "WHERE f.home IS NOT NULL " +
            "  AND f.away IS NOT NULL " +
            "  AND l.predictions IS TRUE " +
            "  AND p.advice NOT LIKE 'No predictions available' AND" +
            "         (f.short_status IN ('FT', 'AET', 'PEN', 'CANC', 'WO', 'Canc', 'AWD', 'TBD') AND f.predictions_updated = false" +
            "         OR (f.short_status NOT IN ('FT', 'AET', 'PEN', 'CANC', 'WO', 'Canc', 'AWD', 'TBD')" +
            "      ))",
            nativeQuery = true)
    List<Long> findUnchecked();

    @Query("SELECT new com.footystars.model.dto.fixture.ClubMatchDto(" +
            "f.id, " +
            "f.info.date, " +
            "f.info.status.shortStatus, " +
            "f.league.leagueId, " +
            "f.league.logo, " +
            "f.league.leagueName, " +
            "f.league.season, " +
            "f.teams.homeTeam.homeId, " +
            "f.teams.homeTeam.homeName, " +
            "f.teams.homeTeam.homeLogo, " +
            "f.score.fulltime.fullTimeHome, " +
            "f.score.fulltime.fullTimeAway, " +
            "f.teams.awayTeam.awayId, " +
            "f.teams.awayTeam.awayName, " +
            "f.teams.awayTeam.awayLogo" +
            ") " +
            "FROM Fixture f " +
            "WHERE (f.teams.homeTeam.homeId = :teamId OR f.teams.awayTeam.awayId = :teamId) " +
            "AND f.info.status.shortStatus IN ('FT', 'AET', 'PEN') " +
            "ORDER BY f.info.date DESC")
    List<ClubMatchDto> findLastMatchesByTeamId(@Param("teamId") Long teamId, Pageable pageable);

    @Query("SELECT DISTINCT new com.footystars.model.dto.fixture.ClubMatchDto(" +
            "f.id, " +
            "f.info.date, " +
            "f.info.status.shortStatus, " +
            "f.league.leagueId, " +
            "f.league.logo, " +
            "f.league.leagueName, " +
            "f.league.season, " +
            "f.teams.homeTeam.homeId, " +
            "f.teams.homeTeam.homeName, " +
            "f.teams.homeTeam.homeLogo, " +
            "f.score.fulltime.fullTimeHome, " +
            "f.score.fulltime.fullTimeAway, " +
            "f.teams.awayTeam.awayId, " +
            "f.teams.awayTeam.awayName, " +
            "f.teams.awayTeam.awayLogo" +
            ") " +
            "FROM Fixture f join League l on f.league.leagueId = l.info.leagueId " +
            "WHERE l.season.current AND" +
            "(f.teams.homeTeam.homeId = :homeId AND f.teams.awayTeam.awayId = :awayId)" +
            "OR (f.teams.homeTeam.homeId = :awayId AND f.teams.awayTeam.awayId = :homeId) " +
            "ORDER BY f.info.date DESC")
    List<ClubMatchDto> findHeadToHeadMatches(Long homeId, Long awayId);

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
                total_matches.total_matches as total_matches,
                (count(*) * 100.0 / total_matches.total_matches) as percentage
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

    @Query("SELECT new com.footystars.model.dto.fixture.MatchDto(" +
            "f.id as id, " +
            "f.info.date as date, " +
            "f.teams.homeTeam.homeId as homeTeamId, " +
            "f.teams.homeTeam.homeName as homeTeamName, " +
            "f.teams.homeTeam.homeLogo as homeTeamLogo, " +
            "f.teams.awayTeam.awayId as awayTeamId, " +
            "f.teams.awayTeam.awayName as awayTeamName, " +
            "f.teams.awayTeam.awayLogo as awayTeamLogo, " +
            "f.league.leagueName as leagueName, " +
            "f.league.logo as leagueLogo, " +
            "f.league.leagueId as leagueId, " +
            "f.league.season as season, " +
            "f.league.round as round, " +
            "f.info.status.elapsed as elapsed, " +
            "f.info.status.shortStatus as status, " +
            "f.goals.home as home, " +
            "f.goals.away as away, " +
            "p.predictions.goals.homePrediction as homePrediction, " +
            "p.predictions.goals.awayPrediction as awayPrediction, " +
            "p.predictions.underOver as underOver, " +
            "p.predictions.advice as advice, " +
            "f.score.halftime.halfTimeHome as halfTimeHome, " +
            "f.score.halftime.halfTimeAway as halfTimeAway, " +
            "f.score.fulltime.fullTimeHome as fullTimeHome, " +
            "f.score.fulltime.fullTimeAway as fullTimeAway, " +
            "f.score.extratime.extraTimeHome as extraTimeHome, " +
            "f.score.extratime.extraTimeAway as extraTimeAway, " +
            "f.score.penalty.penaltiesHome as penaltiesHome, " +
            "f.score.penalty.penaltiesAway as penaltiesAway, " +
            "p.advice as isAdvice, " +
            "p.awayGoals as isAwayGoals, " +
            "p.homeGoals as isHomeGoals, " +
            "p.overUnder as isOverUnder" +
            ") " +
            "FROM Fixture f " +
            "LEFT JOIN f.prediction p " +
            "WHERE f.info.date BETWEEN :startDate AND :endDate")
    List<MatchDto> findMatchDtosByDateRange(@Param("startDate") ZonedDateTime startDate,
                                            @Param("endDate") ZonedDateTime endDate);




    @Query("SELECT new com.footystars.model.dto.fixture.MatchDto(" +
            "f.id, " +
            "f.info.date, " +
            "f.teams.homeTeam.homeId, f.teams.homeTeam.homeName, f.teams.homeTeam.homeLogo, " +
            "f.teams.awayTeam.awayId, f.teams.awayTeam.awayName, f.teams.awayTeam.awayLogo, " +
            "f.league.leagueName, f.league.logo, f.league.leagueId, f.league.season, f.league.round, " +
            "f.info.status.elapsed, f.info.status.shortStatus, " +
            "f.goals.home, f.goals.away, " +
            "f.prediction.predictions.goals.homePrediction, f.prediction.predictions.goals.awayPrediction, " +
            "f.prediction.predictions.underOver, f.prediction.predictions.advice, " +
            "f.score.halftime.halfTimeHome, f.score.halftime.halfTimeAway, " +
            "f.score.fulltime.fullTimeHome, f.score.fulltime.fullTimeAway, " +
            "f.score.extratime.extraTimeHome, f.score.extratime.extraTimeAway, " +
            "f.score.penalty.penaltiesHome, f.score.penalty.penaltiesAway, " +
            "f.prediction.advice, f.prediction.awayGoals, f.prediction.homeGoals, f.prediction.overUnder" +
            ") " +
            "FROM Fixture f " +
            "JOIN League l on f.league.leagueId = l.info.leagueId and l.season.year = f.league.season " +
            "WHERE f.league.leagueId = :leagueId and l.season.current")
    List<MatchDto> findCurrentSeasonMatchesByLeagueId(@Param("leagueId") Long leagueId);

    @Query("SELECT new com.footystars.model.dto.fixture.LeaguePredictionsDto(" +
            "l.leagueId, " +
            "l.leagueName, " +
            "CASE WHEN COUNT(p.advice) = 0 THEN 0.0 ELSE (SUM(CASE WHEN p.advice = true THEN 1.0 ELSE 0.0 END) * 100.0 / COUNT(p.advice)) END, " +
            "CASE WHEN COUNT(p.homeGoals) = 0 THEN 0.0 ELSE (SUM(CASE WHEN p.homeGoals = true THEN 1.0 ELSE 0.0 END) * 100.0 / COUNT(p.homeGoals)) END, " +
            "CASE WHEN COUNT(p.awayGoals) = 0 THEN 0.0 ELSE (SUM(CASE WHEN p.awayGoals = true THEN 1.0 ELSE 0.0 END) * 100.0 / COUNT(p.awayGoals)) END, " +
            "CASE WHEN COUNT(p.overUnder) = 0 THEN 0.0 ELSE (SUM(CASE WHEN p.overUnder = true THEN 1.0 ELSE 0.0 END) * 100.0 / COUNT(p.overUnder)) END" +
            ") " +
            "FROM Fixture f " +
            "JOIN f.league l " +
            "JOIN f.prediction p " +
            "WHERE p.advice IS NOT NULL " +
            "  AND l.leagueId = :leagueId " +
            "GROUP BY l.leagueId, l.leagueName")
    LeaguePredictionsDto findLeaguePredictions(@Param("leagueId") Long leagueId);

    @Query("SELECT DISTINCT new com.footystars.model.dto.fixture.ClubMatchDto(" +
            "f.id, " +
            "f.info.date, " +
            "f.info.status.shortStatus, " +
            "f.league.leagueId, " +
            "f.league.logo, " +
            "f.league.leagueName, " +
            "f.league.season, " +
            "f.teams.homeTeam.homeId, " +
            "f.teams.homeTeam.homeName, " +
            "f.teams.homeTeam.homeLogo, " +
            "f.score.fulltime.fullTimeHome, " +
            "f.score.fulltime.fullTimeAway, " +
            "f.teams.awayTeam.awayId, " +
            "f.teams.awayTeam.awayName, " +
            "f.teams.awayTeam.awayLogo" +
            ") " +
            "FROM Fixture f join League l on f.league.leagueId = l.info.leagueId " +
            "WHERE ((f.info.status.shortStatus = 'FT' AND l.season.current) " +
            "       OR f.info.status.shortStatus <> 'FT') " +
            "  AND (f.teams.homeTeam.homeId = :clubId OR f.teams.awayTeam.awayId = :clubId) " +
            "ORDER BY f.info.date DESC")
    List<ClubMatchDto> findClubMatchDtosByClubIdLeagueId(@Param("clubId") Long clubId);




    @Query("SELECT new com.footystars.model.dto.fixture.TeamPredictionStats(" +
            "  MAX(CASE WHEN f.teams.homeTeam.homeId = :clubId THEN f.teams.homeTeam.homeId ELSE f.teams.awayTeam.awayId END), " +
            "  MAX(CASE WHEN f.teams.homeTeam.homeId = :clubId THEN f.teams.homeTeam.homeName ELSE f.teams.awayTeam.awayName END), " +
            "  CASE WHEN COUNT(p.advice) = 0 THEN 0.0 ELSE (SUM(CASE WHEN p.advice = true THEN 1.0 ELSE 0.0 END) * 100.0 / (COUNT(p.advice) * 1.0)) END, " +
            "  CASE WHEN COUNT(CASE WHEN f.teams.homeTeam.homeId = :clubId THEN p.homeGoals ELSE NULL END) = 0 THEN 0.0 ELSE " +
            "       (SUM(CASE WHEN f.teams.homeTeam.homeId = :clubId AND p.homeGoals = true THEN 1.0 ELSE 0.0 END) * 100.0 / (COUNT(CASE WHEN f.teams.homeTeam.homeId = :clubId THEN p.homeGoals ELSE NULL END) * 1.0)) END, " +
            "  CASE WHEN COUNT(CASE WHEN f.teams.awayTeam.awayId = :clubId THEN p.awayGoals ELSE NULL END) = 0 THEN 0.0 ELSE " +
            "       (SUM(CASE WHEN f.teams.awayTeam.awayId = :clubId AND p.awayGoals = true THEN 1.0 ELSE 0.0 END) * 100.0 / (COUNT(CASE WHEN f.teams.awayTeam.awayId = :clubId THEN p.awayGoals ELSE NULL END) * 1.0)) END, " +
            "  CASE WHEN COUNT(p.overUnder) = 0 THEN 0.0 ELSE (SUM(CASE WHEN p.overUnder = true THEN 1.0 ELSE 0.0 END) * 100.0 / (COUNT(p.overUnder) * 1.0)) END" +
            ") " +
            "FROM Fixture f " +
            "JOIN f.prediction p " +
            "WHERE p.advice IS NOT NULL " +
            "  AND (f.teams.homeTeam.homeId = :clubId OR f.teams.awayTeam.awayId = :clubId)")
    TeamPredictionStats findTeamPredictions(@Param("clubId") Long clubId);

    @Query("SELECT new com.footystars.model.dto.fixture.ClubMatchDto(" +
            "f.id, " +
            "f.info.date, " +
            "f.info.status.shortStatus, " +
            "f.league.logo, " +
            "f.league.leagueName, " +
            "f.teams.homeTeam.homeId, " +
            "f.teams.homeTeam.homeName, " +
            "f.teams.homeTeam.homeLogo, " +
            "f.score.fulltime.fullTimeHome, " +
            "f.score.fulltime.fullTimeAway, " +
            "f.teams.awayTeam.awayId, " +
            "f.teams.awayTeam.awayName, " +
            "f.teams.awayTeam.awayLogo" +
            ") " +
            "FROM Fixture f join League l on f.league.leagueId = l.info.leagueId " +
            "WHERE f.id = :fixtureId " +
            "ORDER BY f.info.date DESC")
    Optional<ClubMatchDto> findFixture(@Param("fixtureId") Long fixtureId);

}



