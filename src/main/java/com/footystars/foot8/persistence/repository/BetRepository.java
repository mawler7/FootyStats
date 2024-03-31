package com.footystars.foot8.persistence.repository;


import com.footystars.foot8.persistence.entity.bet.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<Bet, Long> {

}
