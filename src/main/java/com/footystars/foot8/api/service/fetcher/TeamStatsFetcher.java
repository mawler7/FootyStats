package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.teams.statistics.TeamStatistics;
import com.footystars.foot8.api.service.requester.ParamsProvider;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.business.service.TeamService;
import com.footystars.foot8.business.service.TeamStatsService;
import com.footystars.foot8.exception.TeamStatsException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

import static com.footystars.foot8.utils.LogsNames.FETCHED_ALL_LEAGUES_TEAM_STATS;
import static com.footystars.foot8.utils.LogsNames.NO_TEAMS_FOUND;
import static com.footystars.foot8.utils.LogsNames.TEAM_STATS_ERROR_BY_LEAGUE_ID;
import static com.footystars.foot8.utils.LogsNames.TEAM_STATS_FETCH_ERROR;
import static com.footystars.foot8.utils.PathSegment.TEAMS_STATISTICS;
import static com.footystars.foot8.utils.SelectedLeagues.getSelectedCups;
import static com.footystars.foot8.utils.SelectedLeagues.getFavoritesLeaguesAndCups;

@Service
@RequiredArgsConstructor
public class TeamStatsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final TeamStatsService teamStaticsService;
    private final SeasonService seasonService;
    private final TeamService teamService;
    private final ParamsProvider paramsProvider;

    private final Logger logger = LoggerFactory.getLogger(TeamStatsFetcher.class);

    @Async
    public void fetchByAllLeagues() {
        var ids = getFavoritesLeaguesAndCups();
        ids.forEach(this::fetchByLeagueId);
        logger.info(FETCHED_ALL_LEAGUES_TEAM_STATS);
    }

    public void fetchByLeagueId(@NotNull Long leagueId) {

        try {
            var optionalSeasons = seasonService.findByLeagueId(leagueId);

            optionalSeasons.forEach(season -> {
                var teams = teamService.getByLeagueIdAndSeason(leagueId, season.getYear());

                teams.forEach(team -> {
                    var clubId = team.getClubId();
                    fetchTeamStatistics(clubId, leagueId, season.getYear());
                });
            });

        } catch (Exception e) {
            logger.error(TEAM_STATS_ERROR_BY_LEAGUE_ID, leagueId, e.getMessage());
        }

    }

    public void fetchTeamStatistics(@NotNull Long teamId, @NotNull Long league, @NotNull Integer season) throws TeamStatsException {

        var params = paramsProvider.getTeamLeagueAndSeasonParamsMap(teamId, league, season);

        try {
            var teamsStatsDto = dataFetcher.fetch(TEAMS_STATISTICS, params, TeamStatistics.class).getStatistic();

            if (teamsStatsDto != null) {
                teamStaticsService.fetchTeamStats(teamsStatsDto, params);
            }

        } catch (IOException e) {
            throw new TeamStatsException(e, TEAM_STATS_FETCH_ERROR + e.getMessage());
        }

    }


    public void fetchCurrentSeasonsTeam() {

        var allIds = new ArrayList<Long>();
        var leaguesIds = getFavoritesLeaguesAndCups();
        var cupsIds = getSelectedCups();

        allIds.addAll(leaguesIds);
        allIds.addAll(cupsIds);

        allIds.forEach(this::fetchCurrentSeasonByLeagueId);
    }

    public void fetchCurrentSeasonByLeagueId(@NotNull Long leagueId) {

        var optionalSeason = seasonService.findCurrentSeasonByLeagueId(leagueId);

        if (optionalSeason.isPresent()) {
            var season = optionalSeason.get().getYear();

            var teams = teamService.getByLeagueIdAndSeason(leagueId, season);

            if (!teams.isEmpty()) {
                teams.forEach(t -> {
                    var id = t.getClubId();
                    fetchTeamStatistics(id, leagueId, season);
                });
            } else {
                logger.warn(NO_TEAMS_FOUND, leagueId);
            }
        }


    }

}






