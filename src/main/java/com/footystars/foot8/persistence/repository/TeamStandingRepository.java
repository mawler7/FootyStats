package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entities.teams.standings.Standing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamStandingRepository extends JpaRepository<Standing, Long> {
}