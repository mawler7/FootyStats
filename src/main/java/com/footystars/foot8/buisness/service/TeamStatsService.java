package com.footystars.foot8.buisness.service;


import com.footystars.foot8.persistence.entities.teams.statistics.TeamStats;
import com.footystars.foot8.persistence.entities.teams.statistics.TeamStatsDto;
import com.footystars.foot8.persistence.entities.teams.statistics.TeamStatsMapper;
import com.footystars.foot8.persistence.repository.TeamStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamStatsService {

    private final TeamStatsRepository teamStatisticsRepository;
    private final TeamStatsMapper teamStatsMapper;


    @Transactional
    public void updateFromDto(@NotNull TeamStatsDto teamStatisticsDto) {
        var id = teamStatisticsDto.getTeam().getTeamId();
        var leagueId = teamStatisticsDto.getLeague().getLeagueId();
        var season = teamStatisticsDto.getLeague().getSeasonYear();

        var stats = findByTeamIdSeasonAdnLeagueId(id, Long.valueOf(season), leagueId);
        if(stats.isPresent()) {
//sprawdzic czy jest to current season i zrobic update jesli tak
        }

        var teamStats = teamStatsMapper.toEntity(teamStatisticsDto);
        teamStatisticsRepository.save(teamStats);
    }

    public Optional<TeamStats> findByTeamIdSeasonAdnLeagueId(Long teamId, Long season, Long leagueId) {
        return teamStatisticsRepository.findByTeamIdAndLeagueAndLeagueSeason(teamId, season, leagueId); //
    }

}

