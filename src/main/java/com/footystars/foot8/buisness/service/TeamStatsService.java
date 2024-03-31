package com.footystars.foot8.buisness.service;


import com.footystars.foot8.api.model.teams.statistics.statistic.TeamStatisticApi;
import com.footystars.foot8.persistence.entity.competitions.Competition;
import com.footystars.foot8.persistence.entity.teams.statistics.TeamStatsMapper;
import com.footystars.foot8.persistence.entity.teams.team.Team;
import com.footystars.foot8.persistence.repository.TeamStatsRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;
import static com.footystars.foot8.utils.ParameterNames.TEAM;

@Service
@RequiredArgsConstructor
public class TeamStatsService {

    private final TeamStatsRepository teamStatsRepository;
    private final TeamService teamService;
    private final TeamStatsMapper teamStatsMapper;
    private final CompetitionService competitionService;

    private final Logger logger = LoggerFactory.getLogger(TeamStatsService.class);

    @Transactional
    public void fetchTeamStats(@NotNull TeamStatisticApi teamStatistic, @NotNull Map<String, String> params) {
        var leagueId = Long.valueOf(params.get(LEAGUE));
        var season = Integer.parseInt(params.get(SEASON));
        var clubId = Long.valueOf(params.get(TEAM));

        var optionalCompetition = competitionService.getByLeagueAndSeasonYear(leagueId, season);
        if (optionalCompetition.isPresent()) {
            var competition = optionalCompetition.get();
            var optionalTeam = teamService.getByClubIdLeagueIdAndLeagueSeason(clubId, leagueId, season);

            if (optionalTeam.isPresent()) {
                var team = optionalTeam.get();
                var teamStatsList = team.getStatistics();
                if (teamStatsList == null) {
                    teamStatsList = new HashSet<>();
                }
                var existingStatsForCompetition = teamStatsList.stream()
                        .filter(stats -> stats.getCompetition().equals(competition))
                        .findFirst();

                if (existingStatsForCompetition.isPresent()) {
                    var teamStats = existingStatsForCompetition.get();
                    var teamStatsDto = teamStatsMapper.toDto(teamStatistic);
                    teamStatsMapper.partialUpdate(teamStatsDto, teamStats);
                    teamStats.setDefaults();
                    logger.info("Successfully updated team stats for team {}", team.getClub().getName());
                } else {
                    createTeamStats(teamStatistic, competition, team);
                }
            }
        }
    }

    private void createTeamStats(@NotNull TeamStatisticApi teamStatistic, @NotNull Competition competition, @NotNull Team team) {
        var teamStatsDto = teamStatsMapper.toDto(teamStatistic);
        var teamStats = teamStatsMapper.toEntity(teamStatsDto);
        teamStats.setDefaults();
        teamStats.setTeam(team);
        teamStats.setCompetition(competition);
        teamStats.setDefaults();
        var savedStats = teamStatsRepository.save(teamStats);

        var teamStatistics = team.getStatistics();
        if (teamStatistics == null) {
            teamStatistics = new HashSet<>();
        }

        var iterator = teamStatistics.iterator();
        while (iterator.hasNext()) {
            var stats = iterator.next();
            if (stats.getCompetition().equals(competition)) {
                iterator.remove();
                break;
            }
        }

        teamStatistics.add(savedStats);
        team.setStatistics(teamStatistics);
        logger.info("Successfully saved team stats for team {}", team.getClub().getName());
    }
}