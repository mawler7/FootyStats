package com.footystars.persistence.repository;

import com.footystars.model.entity.PlayerSidelined;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayerSidelinedRepository extends JpaRepository<PlayerSidelined, Long> {

    @Query("SELECT ps from PlayerSidelined ps where ps.playerId =:playerId and ps.sidelined.started =:started and ps.sidelined.ended =:ended")
    Optional<PlayerSidelined> findByPlayerIdAndSidelinedDates(Long playerId, String started, String ended);

}
