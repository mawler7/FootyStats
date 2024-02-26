package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entities.odds.bets.BetEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface BetRepository extends JpaRepository<BetEntity, Long> {

    @NotNull
    Optional<BetEntity> findById(@NotNull Long id);

}
