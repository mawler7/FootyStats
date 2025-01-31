package com.footystars.persistence.repository;

import com.footystars.model.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long> {

    @Query("SELECT l.season.year from League l where l.info.leagueId =:leagueId and l.season.current =:aTrue and l.season.endDate <=:dateTime")
    Optional<Integer> findCurrentSeasonById(Long leagueId, Boolean aTrue, String dateTime);

    @Query("SELECT l from League l where l.info.leagueId =:leagueId ")
    List<League> findByLeagueId(Long leagueId);

    @Query("SELECT l from League l where l.info.leagueId =:leagueId and l.season.year =:year")
    Optional<League> findByLeagueIdAndSeason(Long leagueId, Integer year);

    @Query("SELECT l.season.year from League l where l.info.leagueId =:leagueId and l.season.current =:aTrue ")
    Optional<Integer> findCurrentSeasonByLeagueId(Long leagueId, Boolean aTrue);

    @Query("SELECT l from League l where l.season.current =:aTrue ")
    List<League> findCurrentSeasonLeagues(Boolean aTrue);

}
