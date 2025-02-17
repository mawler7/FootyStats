package com.footystars.persistence.repository;

import com.footystars.model.api.Standings;
import com.footystars.model.dto.league.LeagueInfoDto;
import com.footystars.model.entity.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long> {

    @Query("SELECT l from League l where l.info.leagueId =:leagueId ")
    List<League> findByLeagueId(Long leagueId);

    @Query("SELECT l from League l where l.info.leagueId =:leagueId and l.season.year =:year")
    Optional<League> findByLeagueIdAndSeason(Long leagueId, Integer year);

    @Query("SELECT l.season.year from League l where l.info.leagueId =:leagueId and l.season.current = true ")
    Optional<Integer> findCurrentSeasonByLeagueId(Long leagueId);

    @Query("SELECT DISTINCT new com.footystars.model.dto.league.LeagueInfoDto(" +
            "l.info.leagueId, " +
            "l.info.name, " +
            "l.info.flag, " +
            "l.info.logo, " +
            "l.info.type, " +
            "l.season.year" +
            ") " +
            "FROM Team t " +
            "JOIN t.league l " +
            "WHERE t.info.clubId = :clubId " +
            "  AND (:currentYear - l.season.year <= 1)")
    List<LeagueInfoDto> findLeaguesByClubId(@Param("clubId") Long clubId,
                                            @Param("currentYear") Integer currentYear);


    @Query("""
    SELECT DISTINCT new com.footystars.model.dto.league.LeagueInfoDto(
        l.info.leagueId,
        l.info.name,
        l.info.flag,
        l.info.logo,
        l.info.type,
        l.season.year
    )
    FROM Team t
    JOIN t.league l
    WHERE l.season.current = true
    """)
    List<LeagueInfoDto> findCurrentSeasonLeaguesInfo();

    @Query("""
    SELECT distinct new com.footystars.model.dto.league.LeagueInfoDto(
        l.info.leagueId,
        l.info.name,
        l.info.flag,
        l.info.logo,
        l.info.type,
        l.season.year
    )
    FROM Team t
    JOIN t.league l
    WHERE l.info.leagueId = :leagueId AND l.season.current = true
    """)
    Optional<LeagueInfoDto> findLeagueInfoByLeagueId(@Param("leagueId") Long leagueId);

    @Query("""
    SELECT
        l.standings
    FROM League l
    WHERE l.info.leagueId = :leagueId
    """)
    List<Standings.StandingApi.StandingLeague.Standing> findLeagueStandingsByLeagueId(@Param("leagueId") Long leagueId);

}
