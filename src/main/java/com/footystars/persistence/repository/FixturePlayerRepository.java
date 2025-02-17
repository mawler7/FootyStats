package com.footystars.persistence.repository;

import com.footystars.model.dto.player.PlayerLastMatchDto;
import com.footystars.model.entity.FixturePlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FixturePlayerRepository extends JpaRepository<FixturePlayer, Long> {

    @Query(name = "FixturePlayer.findLastMatchesByPlayerId", nativeQuery = true)
    List<PlayerLastMatchDto> findLastMatchesByPlayerId(@Param("playerId") Long playerId);

}
