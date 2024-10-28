package com.footystars.persistence.repository;

import com.footystars.model.entity.PlayerTrophy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerTrophyRepository extends JpaRepository<PlayerTrophy, Long> {

    @Query("SELECT pt from PlayerTrophy pt where pt.playerId =:playerId and pt.trophy.season =:season and pt.trophy.league =:league")
    Optional<PlayerTrophy> findByPlayerIdAndTrophy(Long playerId, String season, String league);
}
