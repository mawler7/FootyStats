package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.teams.Teams;
import com.footystars.foot8.api.service.params.ParamsProvider;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.business.service.TeamService;
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

import static com.footystars.foot8.utils.PathSegment.TEAMS_INFORMATION;
import static com.footystars.foot8.utils.TopLeagues.getTopLeaguesIds;


@Service
@RequiredArgsConstructor
public class TeamFetcher {

    private final ApiDataFetcher dataFetcher;
    private final SeasonService seasonService;
    private final ParamsProvider paramsProvider;
    private final TeamService teamService;

    private final Logger logger = LoggerFactory.getLogger(TeamFetcher.class);

    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(450, Refill.greedy(450, Duration.ofMinutes(1))))
            .build();
    public void fetchByAllLeagues() {
        getTopLeaguesIds().parallelStream().forEach(this::fetchTeamsByLeagueId);
    }

    @Async
    public void fetchTeamsByLeagueId(@NotNull Long leagueId) {
        try {
            var optionalSeasons = seasonService.findByLeagueId(leagueId);
            optionalSeasons.forEach(season -> {
                try {
                    fetchTeamInfoByLeagueAndSeason(leagueId, season.getYear());
                } catch (Exception e) {
                    logger.error("Error fetching teams for league {} and season {}: {}", leagueId, season, e.getMessage());
                }
            });
        } catch (Exception e) {
            logger.error("Error fetching teams for league {}: {}", leagueId, e.getMessage());
        }
    }

    @Async
    public void fetchTeamInfoByLeagueAndSeason(@NotNull Long league, @NotNull Integer season) {
        if (bucket.tryConsume(1)) {
        try {
            var params = paramsProvider.getLeagueAndSeasonParamsMap(league, season);
            var teamsApiResponse = dataFetcher.fetch(TEAMS_INFORMATION, params, Teams.class);
            if (teamsApiResponse != null) {
                var teamsApi = teamsApiResponse.getTeamsApi();
                teamService.fetchClubs(teamsApi, league, season);
            } else {
                logger.error("Failed to fetch teams for league {} and season {}", league, season);
            }
        } catch (IOException e) {
            logger.error("Error fetching team information for league {} and season {}: {}", league, season, e.getMessage());
        }
        } else {
            logger.warn("Request limit exceeded for league: {}", league);
        }

    }

    public void fetchCurrentSeasonByLeagueId(Long leagueId) {
        try {
            var optionalSeason = seasonService.findCurrentSeasonByLeagueId(leagueId);
            if (optionalSeason.isPresent()) {
                var season = optionalSeason.get().getYear();

                fetchTeamInfoByLeagueAndSeason(leagueId, season);
            } else {
                logger.warn("No current season found for league {}", leagueId);
            }
        } catch (Exception e) {
            logger.error("Error fetching teams for league {}: {}", leagueId, e.getMessage());
        }
    }

    @Async
    public void fetchCurrentSeasonTeamsInfo() {
        var leagues = getTopLeaguesIds();
        leagues.parallelStream().forEach(l -> {
            var optionalSeason = seasonService.findCurrentSeasonByLeagueId(l);
            if (optionalSeason.isPresent()) {
                var season = optionalSeason.get().getYear();
                fetchTeamInfoByLeagueAndSeason(l, season);
            }
        });

    }
}