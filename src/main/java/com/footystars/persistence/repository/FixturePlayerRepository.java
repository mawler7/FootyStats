package com.footystars.persistence.repository;

import com.footystars.model.entity.FixturePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FixturePlayerRepository extends JpaRepository<FixturePlayer, Long> {

    @Query("SELECT fp FROM FixturePlayer fp JOIN FETCH fp.fixture f WHERE fp.player.playerId = :playerId ORDER BY f.info.date DESC")
    List<FixturePlayer> findLastMatchesByPlayerId(Long playerId);

}
