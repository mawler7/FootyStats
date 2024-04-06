package com.footystars.foot8.repository;

import com.footystars.foot8.buisness.model.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeasonRepository extends JpaRepository<Season, Long> {
    Optional<Season> findByLeagueIdAndYear(Long leagueId, Integer year);

    List<Season> findByLeagueId(Long leagueId);
}