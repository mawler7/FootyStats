package com.example.foot8.service.team;

import com.example.foot8.persistence.entities.teams.statistics.TeamStatisticsEntity;
import com.example.foot8.persistence.repository.TeamStatisticsEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamStatisticsService {

    private final TeamStatisticsEntityRepository teamStatisticsRepository;

    @Transactional
    public TeamStatisticsEntity save(TeamStatisticsEntity teamStatisticsEntity) {
        return teamStatisticsRepository.save(teamStatisticsEntity);
    }

    public Optional<TeamStatisticsEntity> getStatsByTeamIdAndAndLeagueIdAndSeason(Long teamId, Long leagueId, Long leagueSeason) {
        return teamStatisticsRepository.findByTeamIdAndLeagueAndLeagueSeason(teamId, leagueId, leagueSeason);

    }
}
