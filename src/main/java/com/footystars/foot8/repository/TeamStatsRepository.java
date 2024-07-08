package com.footystars.foot8.repository;

import com.footystars.foot8.business.model.entity.TeamStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamStatsRepository extends JpaRepository<TeamStats, Long> {
    boolean existsByClubIdAndSeasonYear(Long clubId, int seasonYear);

}
