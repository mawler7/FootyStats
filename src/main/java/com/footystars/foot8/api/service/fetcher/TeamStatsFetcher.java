package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.teams.statistics.TeamStatistics;
import com.footystars.foot8.api.service.params.ParamsProvider;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.business.service.TeamService;
import com.footystars.foot8.business.service.TeamStatsService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;

import static com.footystars.foot8.utils.LogsNames.FETCHED_ALL_LEAGUES_TEAM_STATS;
import static com.footystars.foot8.utils.LogsNames.NO_TEAMS_FOUND;
import static com.footystars.foot8.utils.LogsNames.TEAM_STATS_ERROR_BY_LEAGUE_ID;
import static com.footystars.foot8.utils.PathSegment.TEAMS_STATISTICS;
import static com.footystars.foot8.utils.TopLeagues.getTopLeaguesIds;


@Service
@RequiredArgsConstructor
public class TeamStatsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final TeamStatsService teamStaticsService;
    private final SeasonService seasonService;
    private final TeamService teamService;
    private final ParamsProvider paramsProvider;

    private final Logger logger = LoggerFactory.getLogger(TeamStatsFetcher.class);

    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(450, Refill.greedy(450, Duration.ofMinutes(1))))
            .build();

    @Async
    public void fetchByAllLeagues() {
        var ids = getTopLeaguesIds();
        ids.parallelStream().forEach(this::fetchByLeagueId);
        logger.info(FETCHED_ALL_LEAGUES_TEAM_STATS);
    }

    public void fetchByAllLeaguesCurrentSeason() {
        var ids = getTopLeaguesIds();
        ids.parallelStream().forEach(this::fetchByLeagueIdCurrentSeason);
        logger.info(FETCHED_ALL_LEAGUES_TEAM_STATS);
    }

    @Async
    public void fetchByLeagueId(@NotNull Long leagueId) {
        try {
            var optionalSeasons = seasonService.findByLeagueId(leagueId);
            optionalSeasons.forEach(season -> {
                var teams = teamService.getByLeagueIdAndSeason(leagueId, season.getYear());
                teams.forEach(team -> {
                    var clubId = team.getClubId();
                    fetchTeamStatisticsByTeamIdLeagueIdAndSeason(clubId, leagueId, season.getYear());
                });
            });
        } catch (Exception e) {
            logger.error(TEAM_STATS_ERROR_BY_LEAGUE_ID, leagueId, e.getMessage());
        }
    }

    public void fetchByLeagueIdCurrentSeason(@NotNull Long leagueId) {
        try {
            var optionalSeasons = seasonService.findCurrentSeasonByLeagueId(leagueId);
            if (optionalSeasons.isPresent()) {
                var season = optionalSeasons.get().getYear();
                var teams = teamService.getByLeagueIdAndSeason(leagueId, season);
                teams.parallelStream().forEach(team -> {
                    var clubId = team.getClubId();
                    fetchTeamStatisticsByTeamIdLeagueIdAndSeason(clubId, leagueId, season);
                });
            }
        } catch (Exception e) {
            logger.error(TEAM_STATS_ERROR_BY_LEAGUE_ID, leagueId, e.getMessage());
        }
    }

    @Async
    public void fetchTeamStatisticsByTeamIdLeagueIdAndSeason(@NotNull Long teamId, @NotNull Long league, @NotNull Integer season) {
        if (bucket.tryConsume(1)) {
            var params = paramsProvider.getTeamLeagueAndSeasonParamsMap(teamId, league, season);
            try {
                var teamsStatsDto = dataFetcher.fetch(TEAMS_STATISTICS, params, TeamStatistics.class).getStatistic();
                if (teamsStatsDto != null) {
                    teamStaticsService.fetchTeamStats(teamsStatsDto, params);
                }
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        } else {
            logger.warn("Request limit exceeded for teamId: {}, league: {}, season: {}", teamId, league, season);
        }
    }

    @Async
    public void fetchCurrentSeasonsTeamStats() {
        var leaguesIds = getTopLeaguesIds();
        leaguesIds.parallelStream().forEach(this::fetchCurrentSeasonByLeagueId);
    }

    @Async
    public void fetchCurrentSeasonByLeagueId(@NotNull Long leagueId) {
        var optionalSeason = seasonService.findCurrentSeasonByLeagueId(leagueId);
        if (optionalSeason.isPresent()) {
            var season = optionalSeason.get().getYear();
            var teams = teamService.getByLeagueIdAndSeason(leagueId, season);
            if (!teams.isEmpty()) {
                teams.forEach(t -> {
                    var id = t.getClubId();
                    fetchTeamStatisticsByTeamIdLeagueIdAndSeason(id, leagueId, season);
                });
            } else {
                logger.warn(NO_TEAMS_FOUND, leagueId);
            }
        }
    }


    @Async
    public void updateTeamsStats() {
        try {
            var teamStats = teamStaticsService.getNotUpdatedTeamsStats();
            teamStats.parallelStream().forEach(t -> {
                var year = t.getSeasonYear();
                var leagueId = t.getLeagueId();
                var teamId = t.getClubId();
                fetchTeamStatisticsByTeamIdLeagueIdAndSeason(teamId, leagueId, year);
            });
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
