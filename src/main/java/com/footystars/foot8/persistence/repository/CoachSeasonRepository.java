package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entities.coachs.season.CoachSeason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachSeasonRepository extends JpaRepository<CoachSeason, Long> {
}
