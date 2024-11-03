package com.footystars.persistence.repository;

import com.footystars.model.dto.PlayerCareerDto;
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

    @Query("SELECT new com.footystars.model.dto.PlayerCareerDto(" +
            "p.statistics.league.season, " +
            "p.statistics.club.clubName, " +
            "p.statistics.league.leagueName, " +
            "p.statistics.games.appearances, " +
            "p.statistics.goals.goalsTotal, " +
            "p.statistics.goals.assists, " +
            "p.statistics.cards.yellow, " +
            "p.statistics.cards.red) " +
            "FROM Player p " +
            "WHERE p.info.playerId = :playerId")
    List<PlayerCareerDto> findCareerByPlayerId(@Param("playerId") Long playerId);
}

