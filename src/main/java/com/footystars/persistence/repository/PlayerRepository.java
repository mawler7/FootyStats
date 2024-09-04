package com.footystars.persistence.repository;

import com.footystars.model.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p from Player p where p.statistics.league.leagueId = :leagueId and p.info.playerId =:playerId and p.statistics.league.season = :season and p.statistics.club.clubId = :clubId ")
    Optional<Player> findByPlayerIdLeagueIdSeasonAndClubId(Long playerId, Long leagueId, Integer season, Long clubId);

    @Query("SELECT distinct(p.info.playerId) from Player p where p.statistics.league.leagueId = :leagueId")
    List<Long> findPlayerIdByLeagueId(Long leagueId);

}