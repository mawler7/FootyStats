package com.footystars.foot8.repository;


import com.footystars.foot8.business.model.entity.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<Bet, Long> {

}
