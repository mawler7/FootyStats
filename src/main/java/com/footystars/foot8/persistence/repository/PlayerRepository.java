package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entity.players.player.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}