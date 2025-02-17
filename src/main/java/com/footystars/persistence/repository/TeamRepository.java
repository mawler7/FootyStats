package com.footystars.persistence.repository;

import com.footystars.model.dto.team.ClubDto;
import com.footystars.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t.info.clubId FROM Team t WHERE t.league.info.leagueId = :leagueId AND t.league.season.year = :year")
    List<Long> findClubIdsByLeagueIdAndSeasonYear(Long leagueId, Integer year);

    @Query("SELECT t FROM Team t WHERE t.league.info.leagueId = :leagueId AND t.league.season.year = :year AND t.info.clubId = :clubId")
    Optional<Team> findByInfoClubIdAndSeasonYearAndSeasonLeagueLeagueId(Long leagueId, Integer year, Long clubId);

    @Query("""
                SELECT t FROM Team t
                JOIN FETCH t.league l
                JOIN FETCH l.season s
                WHERE t.info.clubId = :clubId AND s.current = :aTrue
            """)
    List<Team> findByIdAndLeagueSeasonCurrent(Long clubId, Boolean aTrue);

    @Query("SELECT new com.footystars.model.dto.team.ClubDto(" +
            "t.info.clubId, " +
            "t.info.teamName, " +
            "t.info.logo, " +
            "t.venue.venueName, " +
            "t.venue.image, " +
            "t.venue.capacity, " +
            "t.coach.name " +
            ") " +
            "FROM Team t " +
            "WHERE t.info.clubId = :clubId")
    Optional<ClubDto> findClubBasicInfo(@Param("clubId") Long clubId);

    @Query("SELECT t FROM Team t WHERE t.info.clubId = :clubId ")
    List<Team> findByInfoClubId(@Param("clubId") Long clubId);


}

