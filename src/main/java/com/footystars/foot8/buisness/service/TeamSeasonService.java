package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeason;
import com.footystars.foot8.persistence.repository.TeamSeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamSeasonService {

    private final TeamSeasonRepository teamSeasonsRepository;
    public Optional<TeamSeason> findByTeamIdLeagueIdAndSeasonYear(Long teamId, Long leagueId, int seasonYear) {
        return teamSeasonsRepository.findByTeamIdAndLeagueSeasonLeagueIdAndLeagueSeasonYear(teamId, leagueId, seasonYear);
    }

    @Transactional
    public TeamSeason save(TeamSeason teamSeasons) {
        return teamSeasonsRepository.save(teamSeasons);
    }

    public List<TeamSeason> findTeamIdsByLeagueIdAndSeason(Long id, int season) {
        return teamSeasonsRepository.findByLeagueSeasonLeagueIdAndLeagueSeasonYear(id, season);
    }

    public Optional<TeamSeason> findCurrentByTeamId(Long teamId) {
        return teamSeasonsRepository.findByLeagueSeasonCurrentTrueAndTeamId(teamId);
    }

}
