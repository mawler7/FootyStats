package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entity.teams.statistics.TeamStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamStatsRepository extends JpaRepository<TeamStats, Long> {

}
