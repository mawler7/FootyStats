package com.footystars.persistence.repository;


import com.footystars.model.entity.TeamStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamStatsRepository extends JpaRepository<TeamStats, Long> {
}
