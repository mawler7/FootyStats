package com.example.foot8.persistence.repository;

import com.example.foot8.persistence.entities.teams.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    @Query("SELECT t.id FROM TeamEntity t WHERE t.league.id =?1")
    List<Long> findTeamIdsByLeagueId(Long leagueId);

}
