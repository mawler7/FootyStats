package com.footystars.persistence.repository;

import com.footystars.model.dto.player.PlayerDto;
import com.footystars.model.dto.player.PlayerTeamSquadDto;
import com.footystars.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p from Player p where p.statistics.league.leagueId = :leagueId and p.info.playerId =:playerId and p.statistics.league.season = :season and p.statistics.club.clubId = :clubId ")
    Optional<Player> findByPlayerIdLeagueIdSeasonAndClubId(Long playerId, Long leagueId, Integer season, Long clubId);

    @Query("SELECT distinct(p.info.playerId) from Player p where p.statistics.league.leagueId = :leagueId")
    List<Long> findPlayerIdByLeagueId(Long leagueId);

    @Query("SELECT p from Player p where p.statistics.league.leagueId = :leagueId and  p.statistics.club.clubId = :clubId and p.statistics.league.season = :season")
    List<Player> findByLeagueIdClubIdAndSeason(Long clubId, Long leagueId,  int season);

    @Query("SELECT p from Player p where p.info.playerId = :playerId")
    List<Player> findByInfoPlayerId(Long playerId);

    @Query("SELECT distinct(p.info.playerId) from Player p where p.statistics.club.clubId = :clubId")
    List<Long> findByInfoClubId(Long clubId);

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

    @Query("SELECT new com.footystars.model.dto.player.PlayerTeamSquadDto(" +
            "p.info.playerId, " +                           // id (oraz player_id)
            "p.info.birth.birthCountry, " +                 // birth_country
            "p.info.birth.birthDate, " +                    // birth_date
            "p.info.birth.birthPlace, " +                   // birth_place
            "p.info.firstname, " +                          // firstname
            "p.info.injured, " +                            // injured
            "p.info.lastname, " +                           // lastname
            "p.info.name, " +                               // name
            "p.info.nationality, " +                        // nationality
            "p.info.photo, " +                              // photo
            "p.info.playerId, " +                           // player_id (powtórzenie id, jeśli potrzebne osobno)
            "p.info.zodiac, " +                             // zodiac
            "stats.cards.red, " +                           // red
            "stats.cards.yellow, " +                        // yellow
            "stats.cards.yellowRed, " +                     // yellow_red
            "stats.club.clubId, " +                         // club_id
            "stats.club.clubName, " +                       // club_name
            "stats.dribbles.attempts, " +                   // attempts
            "stats.dribbles.past, " +                       // past
            "stats.dribbles.success, " +                    // success
            "stats.duels.duelsTotal, " +                    // duels_total
            "stats.duels.duelsWon, " +                      // duels_won
            "stats.fouls.committed, " +                     // committed
            "stats.fouls.drawn, " +                         // drawn
            "stats.games.appearances, " +                   // appearances
            "stats.games.captain, " +                       // captain
            "stats.games.lineups, " +                       // lineups
            "stats.games.minutes, " +                       // minutes
            "stats.games.number, " +                        // number
            "stats.games.position, " +                      // position
            "stats.games.rating, " +                        // rating
            "stats.goals.assists, " +                       // assists
            "stats.goals.conceded, " +                      // conceded
            "stats.goals.goalsTotal, " +                    // goals_total
            "stats.goals.saves, " +                         // saves
            "stats.league.countryName, " +                  // country_name
            "stats.league.leagueId, " +                     // league_id
            "stats.league.leagueName, " +                   // league_name
            "stats.league.logo, " +                         // logo
            "stats.league.season, " +                       // season
            "stats.passes.accuracy, " +                     // accuracy
            "stats.passes.key, " +                          // key
            "stats.passes.passesTotal, " +                  // passes_total
            "stats.penalty.penaltiesCommitted, " +          // penalties_committed
            "stats.penalty.penaltiesMissed, " +             // penalties_missed
            "stats.penalty.penaltiesSaved, " +              // penalties_saved
            "stats.penalty.penaltiesScored, " +             // penalties_scored
            "stats.penalty.penaltiesWon, " +                // penalties_won
            "stats.shots.shotsOnTarget, " +                 // shots_on_target
            "stats.shots.shotsTotal, " +                    // shots_total
            "stats.substitutes.bench, " +                   // bench
            "stats.substitutes.substitutedIn, " +           // substituted_in
            "stats.substitutes.substitutedOut, " +          // substituted_out
            "stats.tackles.blocks, " +                      // blocks
            "stats.tackles.interceptions, " +               // interceptions
            "stats.tackles.tacklesTotal, " +                // tackles_total
            "stats.club.clubLogo" +                         // club_logo
            ") " +
            "FROM Player p JOIN p.statistics stats " +
            "WHERE stats.club.clubId = :clubId " +
            "  AND stats.league.leagueId = :leagueId " +
            "  AND stats.league.season = :season")
    List<PlayerTeamSquadDto> findPlayersByLeagueAndClub(@Param("clubId") Long clubId,
                                                        @Param("leagueId") Long leagueId,
                                                        @Param("season") Integer season);

}




