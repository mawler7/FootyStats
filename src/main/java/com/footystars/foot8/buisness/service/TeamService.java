package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entity.teams.team.Team;
import com.footystars.foot8.persistence.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public Team save(Team team) {
        return teamRepository.save(team);
    }

    public Optional<Team> findById(Long teamId) {
        return teamRepository.findById(teamId);
    }

    public Optional<Team> getCurrentSeasonTeamByClubId(Long clubId) {
        return teamRepository.findByCompetitionsSeasonCurrentTrueAndClubId(clubId);
    }

    public Optional<Team> getByClubIdLeagueIdAndLeagueSeason(Long clubId, Long leagueId, Integer season) {
        return teamRepository.findByClubIdAndCompetitionsLeagueIdAndCompetitionsSeasonYear(clubId, leagueId, season);
    }

}
