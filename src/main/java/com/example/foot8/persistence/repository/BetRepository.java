package com.example.foot8.persistence.repository;

import com.example.foot8.persistence.entities.odds.bets.BetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BetRepository extends JpaRepository<BetEntity, Long> {

    Optional<BetEntity> findById(Long id);

}
