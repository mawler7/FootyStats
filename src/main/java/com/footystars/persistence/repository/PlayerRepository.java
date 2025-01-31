package com.footystars.persistence.repository;

import com.footystars.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p from Player p where p.statistics.league.leagueId = :leagueId and p.info.playerId =:playerId and p.statistics.league.season = :season and p.statistics.club.clubId = :clubId ")
    Optional<Player> findByPlayerIdLeagueIdSeasonAndClubId(Long playerId, Long leagueId, Integer season, Long clubId);

    @Query("SELECT distinct(p.info.playerId) from Player p where p.statistics.league.leagueId = :leagueId")
    List<Long> findPlayerIdByLeagueId(Long leagueId);


    @Query("SELECT p from Player p where p.statistics.club.clubId = :clubId and p.statistics.league.season = :season")
    List<Player> findByClubIdAndSeason(Long clubId, int season);

    @Query("SELECT p from Player p where p.statistics.league.leagueId = :leagueId and  p.statistics.club.clubId = :clubId and p.statistics.league.season = :season")
    List<Player> findByLeagueIdClubIdAndSeason(Long clubId, Long leagueId,  int season);

    @Query("SELECT p from Player p where p.info.playerId = :playerId")
    List<Player> findByInfoPlayerId(Long playerId);

    @Query(value = """
    SELECT 
        p.player_id AS playerId,
        p.name AS name,
        p.nationality AS nationality,
        p.photo AS photo,
        p.club_name AS clubName,
        p.club_logo AS clubLogo,
        p.position AS position,
        p.rating AS form,
        p.appearances AS appearances,
        p.goals_total AS goals,
        p.assists AS assists,
        p.yellow AS yellowCards,
        p.red AS redCards,
        p.shots_on_target AS shotsOnTarget,
        p.shots_total AS totalShots,
        p.penalties_scored AS penaltiesScored,
        p.penalties_missed AS penaltiesMissed,
        p.minutes AS minutesPlayed,
        p.key AS keyPasses,
        p.duels_total AS duelsTotal,
        p.duels_won AS duelsWon
    FROM players p
    WHERE p.league_id = :leagueId 
      AND p.season = (
          SELECT MAX(sub.season) 
          FROM players sub 
          WHERE sub.league_id = :leagueId
      )
      AND p.goals_total IS NOT NULL 
      AND p.goals_total > 0
    ORDER BY p.goals_total DESC
    LIMIT 250
""", nativeQuery = true)
    List<Object[]> findTopScorersByLeagueId(@Param("leagueId") int leagueId);


}




