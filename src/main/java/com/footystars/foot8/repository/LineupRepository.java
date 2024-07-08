package com.footystars.foot8.repository;

import com.footystars.foot8.business.model.entity.Lineup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineupRepository extends JpaRepository<Lineup, Long> {
}
