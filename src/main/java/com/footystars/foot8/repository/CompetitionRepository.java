package com.footystars.foot8.repository;

import com.footystars.foot8.buisness.model.entity.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    Optional<Competition> findByLeagueIdAndSeasonYear(Long leagueId, int year);

    List<Competition> findByLeagueId(Long leagueId);
}
