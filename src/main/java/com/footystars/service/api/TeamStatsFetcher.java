package com.footystars.service.api;

import com.footystars.model.api.TeamStatistics;
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

import static com.footystars.utils.LogsNames.LIMIT_EXCEEDED;
import static com.footystars.utils.LogsNames.NO_TEAMS_FOUND;
import static com.footystars.utils.LogsNames.TEAMS_STATS_FETCHED;
import static com.footystars.utils.LogsNames.TEAMS_STATS_FETCHED_BY_LEAGUE_AND_SEASON;
import static com.footystars.utils.PathSegment.TEAMS_STATISTICS;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class TeamStatsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final LeagueService leagueService;
    private final TeamService teamService;
    private final ParamsProvider paramsProvider;

    private final Logger logger = LoggerFactory.getLogger(TeamStatsFetcher.class);

    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(300, Refill.greedy(300, Duration.ofMinutes(1))))
            .build();


    @Async
    public void fetchTopLeaguesTeamsStatsByAllTime() {
        getTopLeaguesIds().forEach(this::fetchAllTimeTeamsStatsByLeagueId);
    }

    public void fetchAllTimeTeamsStatsByLeagueId(@NotNull Long leagueId) {
        var leagues = leagueService.findByLeagueId(leagueId);
        leagues.forEach(league -> {
            var season = league.getSeason().getYear();
            var clubIds = teamService.getClubIdsByLeagueIdAndSeasonYear(leagueId, season);
            clubIds.forEach(clubId -> fetchTeamStatisticsByClubIdLeagueIdAndSeason(clubId, leagueId, season));
            logger.info(TEAMS_STATS_FETCHED_BY_LEAGUE_AND_SEASON, leagueId, season);
        });
    }

    public void fetchTeamStatisticsByClubIdLeagueIdAndSeason(Long clubId, Long leagueId, Integer season) {
        if (bucket.tryConsume(1)) {
            try {
                var params = paramsProvider.getTeamLeagueAndSeasonParamsMap(clubId, leagueId, season);
                var response = dataFetcher.fetch(TEAMS_STATISTICS, params, TeamStatistics.class).getResponse();
                if (response != null) {
                    teamService.fetchTeamStats(response, params);
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }

        } else {
            logger.warn(LIMIT_EXCEEDED, clubId, leagueId, season);
        }
    }

    @Async
    public void fetchCurrentSeasonsTeamStats() {
        getTopLeaguesIds().forEach(this::fetchCurrentSeasonByLeagueId);
        logger.info(TEAMS_STATS_FETCHED);
    }

    public void fetchCurrentSeasonByLeagueId(@NotNull Long leagueId) {
        var optionalSeason = leagueService.findCurrentSeasonByLeagueId(leagueId);
        if (optionalSeason.isPresent()) {
            var seasonYear = optionalSeason.get();
            var clubIds = teamService.getClubIdsByLeagueIdAndSeasonYear(leagueId, seasonYear);
            if (!clubIds.isEmpty()) {
                clubIds.forEach(clubId -> fetchTeamStatisticsByClubIdLeagueIdAndSeason(clubId, leagueId, seasonYear));
            } else {
                logger.warn(NO_TEAMS_FOUND, leagueId);
            }
        }
    }

}

