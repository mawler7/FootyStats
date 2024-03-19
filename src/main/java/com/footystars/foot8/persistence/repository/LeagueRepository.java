package com.footystars.foot8.persistence.repository;


import com.footystars.foot8.persistence.entities.leagues.league.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long> {
}