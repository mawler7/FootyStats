package com.footystars.foot8.buisness.service;


import com.footystars.foot8.api.model.teams.statistics.statistic.TeamStatistic;
import com.footystars.foot8.persistence.entities.teams.statistics.TeamStats;
import com.footystars.foot8.persistence.entities.teams.statistics.TeamStatsMapper;
import com.footystars.foot8.persistence.repository.TeamStatsRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TeamStatsService {

    private final TeamStatsRepository teamStatsRepository;
    private final TeamStatsMapper teamStatsMapper;
    private final TeamSeasonService teamSeasonService;


    @Transactional
    @Async
    public void fetchTeamStats(@NotNull TeamStatistic teamStatistic) {

        var teamId = teamStatistic.getTeam().getTeamId();
        var leagueId = teamStatistic.getLeague().getLeagueId();
        var season = teamStatistic.getLeague().getSeason();

        var optionalTeamSeason = teamSeasonService.findByTeamIdLeagueIdAndSeasonYear(teamId, leagueId, season);
        if (optionalTeamSeason.isPresent()) {
            var teamSeason = optionalTeamSeason.get();
            if (teamSeason.getTeamStats() == null) {
                var teamStatsDto = teamStatsMapper.toDto(teamStatistic);
                var teamStats = teamStatsMapper.toEntity(teamStatsDto);
                var savedStats = save(teamStats);
                teamSeason.setTeamStats(savedStats);
            } else {
                var teamStats = teamSeason.getTeamStats();
                teamStatsMapper.partialUpdate(teamStatistic, teamStats);
            }
        }
    }

    @Transactional
    public TeamStats save(TeamStats teamStats) {
        return teamStatsRepository.save(teamStats);
    }

}

