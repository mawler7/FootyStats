package com.footystars.foot8.repository;

import com.footystars.foot8.business.model.entity.TeamStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.ZonedDateTime;
import java.util.List;

public interface TeamStatsRepository extends JpaRepository<TeamStats, Long> {
    boolean existsByClubIdAndSeasonYear(Long clubId, int seasonYear);
    @Query("SELECT t.statistics " +
            "FROM Team t " +
            "JOIN Fixture f ON t.id = f.homeTeam.id OR t.id = f.awayTeam.id " +
            "WHERE FUNCTION('DATE', f.date) = CURRENT_DATE " +
            "AND CURRENT_TIMESTAMP > f.date " +
            "AND f.status <> 'Match Finished'")
    List<TeamStats> findClubsWithUpdatedStatsBeforeMatchStart();
}
