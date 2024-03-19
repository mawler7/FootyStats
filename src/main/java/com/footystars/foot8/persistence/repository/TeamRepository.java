package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entities.teams.team.Team;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeamRepository extends JpaRepository<Team, Long> {
}