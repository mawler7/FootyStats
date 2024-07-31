package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.teams.statistics.TeamStatistics;
import com.footystars.foot8.api.service.requester.ParamsProvider;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.business.service.teams.TeamService;
import com.footystars.foot8.business.service.teams.TeamStatsService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public void fetchByAllLeagues() {
        var ids = getTopLeaguesIds();
        ids.forEach(this::fetchByLeagueId);
        logger.info(FETCHED_ALL_LEAGUES_TEAM_STATS);
    }

    public void fetchByAllLeaguesCurrentSeason() {
        var ids = getTopLeaguesIds();
        ids.parallelStream().forEach(this::fetchByLeagueIdCurrentSeason);
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

    public void fetchByLeagueIdCurrentSeason(@NotNull Long leagueId) {
        try {
            var optionalSeasons = seasonService.findCurrentSeasonByLeagueId(leagueId);
            if (optionalSeasons.isPresent()) {
                var season = optionalSeasons.get().getYear();
                var teams = teamService.getByLeagueIdAndSeason(leagueId, season);
                teams.parallelStream().forEach(team -> {
                    var clubId = team.getClubId();
                    fetchTeamStatistics(clubId, leagueId, season);
                });
            }
        } catch (Exception e) {
            logger.error(TEAM_STATS_ERROR_BY_LEAGUE_ID, leagueId, e.getMessage());
        }
    }

    public void fetchTeamStatistics(@NotNull Long teamId, @NotNull Long league, @NotNull Integer season) {
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

    public void fetchCurrentSeasonsTeamStats() {
        var leaguesIds = getTopLeaguesIds();
        leaguesIds.forEach(this::fetchCurrentSeasonByLeagueId);
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
