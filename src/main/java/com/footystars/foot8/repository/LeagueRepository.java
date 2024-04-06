package com.footystars.foot8.repository;


import com.footystars.foot8.buisness.model.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long> {
}