package com.footystars.foot8.repository;

import com.footystars.foot8.business.model.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeasonRepository extends JpaRepository<Season, Long> {
    Optional<Season> findByLeagueIdAndYear( Long leagueId,  Integer year);

    boolean existsByYearAndLeagueId(int year, Long leagueId);

    List<Season> findAllByLeagueId(Long leagueId);

    @Query("SELECT s FROM Season s WHERE s.league.id = :leagueId AND s.current = true")
    Optional<Season> findByLeagueIdAndCurrentTrue(Long leagueId);

    Optional<Season> findByYearAndLeagueId(int year, Long leagueId);

    boolean existsByLeagueIdAndYearAndCurrentTrue(Long leagueId, Integer year);

}