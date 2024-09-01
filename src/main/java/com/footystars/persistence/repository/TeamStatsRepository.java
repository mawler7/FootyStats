package com.footystars.persistence.repository;

import com.footystars.foot8.business.model.entity.TeamStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface TeamStatsRepository extends JpaRepository<TeamStats, Long> {
}
