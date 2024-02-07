package com.example.foot8.persistence.repository;

import com.example.foot8.persistence.entities.leagues.LeagueEntity;
import com.example.foot8.persistence.entities.seasons.SeasonsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeasonsEntityRepository extends JpaRepository<SeasonsEntity, Long> {

    Optional<SeasonsEntity> findByYearAndStartDateAndEndDate(Integer year, String startDate, String endDate);

    List<SeasonsEntity> findByYearAndStartDateAndEndDateAndLeague(Integer year, String startDate, String endDate, LeagueEntity leagueEntity);

    Optional<SeasonsEntity> findByLeagueIdAndCurrentTrue(Long leagueId);


}