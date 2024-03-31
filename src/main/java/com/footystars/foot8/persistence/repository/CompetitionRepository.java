package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entity.competitions.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    Optional<Competition> findByLeagueIdAndSeasonYear(Long leagueId, int year);
}
