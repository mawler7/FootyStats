package com.footystars.foot8.persistence.repository;


import com.footystars.foot8.persistence.entity.leagues.League;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, Long> {
}