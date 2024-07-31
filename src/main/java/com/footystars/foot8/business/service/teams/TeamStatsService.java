package com.footystars.foot8.business.service.teams;


import com.footystars.foot8.api.model.teams.statistics.statistic.TeamStatisticApi;
import com.footystars.foot8.business.model.entity.Team;
import com.footystars.foot8.business.model.entity.TeamStats;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.mapper.TeamStatsMapper;
import com.footystars.foot8.repository.TeamStatsRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Map;

import static com.footystars.foot8.utils.LogsNames.TEAM_NOT_FOUND_EXCEPTION;
import static com.footystars.foot8.utils.LogsNames.TEAM_STATS_CREATED;
import static com.footystars.foot8.utils.LogsNames.TEAM_STATS_ERROR;
import static com.footystars.foot8.utils.LogsNames.TEAM_UPDATED;
import static com.footystars.foot8.utils.ParameterName.LEAGUE;
import static com.footystars.foot8.utils.ParameterName.SEASON;
import static com.footystars.foot8.utils.ParameterName.TEAM;

@Service
@RequiredArgsConstructor
public class TeamStatsService {

    private final TeamStatsRepository teamStatsRepository;
    private final TeamService teamService;
    private final TeamStatsMapper teamStatsMapper;
    private final SeasonService seasonService;

    private final Logger logger = LoggerFactory.getLogger(TeamStatsService.class);


    @Transactional
    public void fetchTeamStats(@NotNull TeamStatisticApi teamStatistic, @NotNull Map<String, String> params) {

        var leagueId = Long.valueOf(params.get(LEAGUE));
        var season = Integer.parseInt(params.get(SEASON));
        var clubId = Long.valueOf(params.get(TEAM));

        var optionalTeam = teamService.getByClubIdLeagueIdAndYear(clubId, leagueId, season);

        if (optionalTeam.isPresent()) {
            var team = optionalTeam.get();
            var currentSeasonByLeagueId = seasonService.findCurrentSeasonByLeagueId(leagueId);
            var statistics = team.getStatistics();

            if (statistics != null) {
                if (currentSeasonByLeagueId.isPresent()) {
                    var currentSeason = currentSeasonByLeagueId.get().getYear();

                    if (season == currentSeason) {
                        var teamStatsDto = teamStatsMapper.toDto(teamStatistic);
                        teamStatsMapper.partialUpdate(teamStatsDto, statistics);
                        statistics.setLastUpdated(ZonedDateTime.now());
                        teamStatsRepository.save(statistics);
                        logger.info(TEAM_UPDATED, team.getName(), leagueId, season);
                    }
                }
            } else {
                createNewTeamStats(teamStatistic, team, leagueId, season);
            }
        } else {
            logger.warn(TEAM_NOT_FOUND_EXCEPTION, clubId, leagueId, season);
        }
    }

    @Transactional
    public void createNewTeamStats(@NotNull TeamStatisticApi teamStatistic, @NotNull Team team, @NotNull Long leagueId, @NotNull Integer season) {
        var teamStatsDto = teamStatsMapper.toDto(teamStatistic);
        var stats = teamStatsMapper.toEntity(teamStatsDto);
        try {
            TeamStats teamStats = teamStatsRepository.save(stats);
            team.setStatistics(teamStats);
            teamService.save(team);
            logger.info(TEAM_STATS_CREATED, team.getName(), leagueId, season);
        } catch (DataIntegrityViolationException e) {
            logger.error(TEAM_STATS_ERROR, team.getName(), leagueId, season, e.getMessage());
        }
    }
}