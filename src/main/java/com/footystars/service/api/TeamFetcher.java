package com.footystars.service.api;

import com.footystars.model.api.TeamsInfo;
import com.footystars.service.business.LeagueService;
import com.footystars.service.business.TeamService;
import com.footystars.utils.ParamsProvider;
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

import static com.footystars.utils.LogsNames.FETCH_CLUBS_ERROR;
import static com.footystars.utils.LogsNames.LIMIT_EXCEEDED_LEAGUE;
import static com.footystars.utils.LogsNames.NO_CURRENT_SEASON_FOUND;
import static com.footystars.utils.LogsNames.TEAMS_FETCH_FAILED;
import static com.footystars.utils.LogsNames.TEAMS_INFO_FETCHED;
import static com.footystars.utils.LogsNames.TEAMS_INFO_FETCHED_BY_LEAGUE_AND_SEASON;
import static com.footystars.utils.LogsNames.TEAMS_INFO_FETCH_ERROR;
import static com.footystars.utils.PathSegment.TEAMS_INFORMATION;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;


@Service
@RequiredArgsConstructor
public class TeamFetcher {

    private final ApiDataFetcher dataFetcher;
    private final LeagueService leagueService;
    private final ParamsProvider paramsProvider;
    private final TeamService teamService;

    private final Logger logger = LoggerFactory.getLogger(TeamFetcher.class);

    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(450, Refill.greedy(450, Duration.ofMinutes(1))))
            .build();

    @Async
    public void fetchByAllLeagues() {
        getTopLeaguesIds().forEach(this::fetchTeamsByLeagueId);
        logger.info(TEAMS_INFO_FETCHED);
    }

    public void fetchTeamsByLeagueId(@NotNull Long leagueId) {
        var leagues = leagueService.findByLeagueId(leagueId);
        leagues.forEach(l -> {
            int year = l.getSeason().getYear();
            fetchTeamInfoByLeagueAndSeason(leagueId, year);
            logger.info(TEAMS_INFO_FETCHED_BY_LEAGUE_AND_SEASON, leagueId, year);
        });
    }

    public void fetchTeamInfoByLeagueAndSeason(@NotNull Long league, @NotNull Integer season) {
        if (bucket.tryConsume(1)) {
            try {
                var params = paramsProvider.getLeagueAndSeasonParamsMap(league, season);
                var teamsApiResponse = dataFetcher.fetch(TEAMS_INFORMATION, params, TeamsInfo.class);
                if (teamsApiResponse != null) {
                    fetchClubs(teamsApiResponse, league, season);
                } else {
                    logger.error(TEAMS_FETCH_FAILED, league, season);
                }
            } catch (IOException e) {
                logger.error(TEAMS_INFO_FETCH_ERROR, league, season, e.getMessage());
            }
        } else {
            logger.warn(LIMIT_EXCEEDED_LEAGUE, league);
        }
    }

    public void fetchCurrentSeasonByLeagueId(Long leagueId) {
        var optionalSeason = leagueService.findCurrentSeasonByLeagueId(leagueId);
        if (optionalSeason.isPresent()) {
            var season = optionalSeason.get();
            fetchTeamInfoByLeagueAndSeason(leagueId, season);
        } else {
            logger.warn(NO_CURRENT_SEASON_FOUND, leagueId);
        }
    }

    @Async
    public void fetchCurrentSeasonTeamsInfo() {
        var leagues = getTopLeaguesIds();
        leagues.parallelStream().forEach(l -> {
            var optionalSeason = leagueService.findCurrentSeasonByLeagueId(l);
            if (optionalSeason.isPresent()) {
                var season = optionalSeason.get();
                fetchTeamInfoByLeagueAndSeason(l, season);
            }
        });
    }

    public void fetchClubs(@NotNull TeamsInfo teams, @NotNull Long leagueId, @NotNull Integer year) {
        try {
            teams.getResponse()
                    .parallelStream()
                    .forEach(t -> teamService.fetchTeams(leagueId, year, t));
        } catch (Exception e) {
            logger.error(FETCH_CLUBS_ERROR, e);
        }
    }

}