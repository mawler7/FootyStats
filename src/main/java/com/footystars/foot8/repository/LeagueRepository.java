package com.footystars.foot8.repository;


import com.footystars.foot8.business.model.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long> {
}