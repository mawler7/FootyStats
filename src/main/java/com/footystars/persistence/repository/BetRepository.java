package com.footystars.persistence.repository;

import com.footystars.model.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<Bet, Long> {
}
