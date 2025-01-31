package com.footystars.service.api;

import com.footystars.exception.CoachFetchingException;
import com.footystars.model.api.Coaches;
import com.footystars.service.business.CoachService;
import com.footystars.service.business.LeagueService;
import com.footystars.service.business.TeamService;
import com.footystars.exception.FetchLeaguesException;
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
import java.util.Comparator;
import java.util.HashMap;

import static com.footystars.utils.LogsNames.*;
import static com.footystars.utils.ParameterName.TEAM;
import static com.footystars.utils.PathSegment.COACHS;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

/**
 * Service responsible for fetching coach data from an external API.
 * Uses rate limiting with {@link Bucket4j} to prevent exceeding API limits.
 */
@Service
@RequiredArgsConstructor
public class CoachesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final CoachService coachService;
    private final TeamService teamService;
    private final LeagueService leagueService;

    private final Logger log = LoggerFactory.getLogger(CoachesFetcher.class);

    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(450, Refill.greedy(450, Duration.ofMinutes(1))))
            .build();

    /**
     * Fetches coaches for all teams in top leagues asynchronously.
     * Calls {@link #fetchCoachesByLeagueIdInCurrentSeason(Long)} for each league.
     */
    @Async
    public void fetchTopLeaguesCoaches() {
        getTopLeaguesIds().forEach(this::fetchCoachesByLeagueIdInCurrentSeason);
        log.info(COACHES_FETCHED);
    }

    /**
     * Fetches coaches for all teams in a given league during the current season.
     *
     * @param leagueId The ID of the league.
     */
    public void fetchCoachesByLeagueIdInCurrentSeason(@NotNull Long leagueId) {
        var optionalSeason = leagueService.findCurrentSeasonByLeagueId(leagueId);

        if (optionalSeason.isPresent()) {
            var year = optionalSeason.get();
            var clubIds = teamService.getClubIdsByLeagueIdAndSeasonYear(leagueId, year);

            clubIds.forEach(clubId -> {
                try {
                    fetchCoachByClubId(clubId);
                } catch (FetchLeaguesException e) {
                    log.error(COACHES_ERROR, leagueId, e.getMessage());
                }
            });
            log.info(COACH_BY_LEAGUE_FETCHED, leagueId);
        }
    }

    /**
     * Fetches a coach for a given club by making an API request.
     * Uses a rate limiter to prevent exceeding API request limits.
     *
     * @param clubId The ID of the club.
     * @throws CoachFetchingException If an error occurs while fetching coach data.
     */
    public void fetchCoachByClubId(Long clubId) throws CoachFetchingException {
        if (bucket.tryConsume(1)) {
            var params = new HashMap<String, String>();
            params.put(TEAM, String.valueOf(clubId));

            try {
                var response = dataFetcher.fetch(COACHS, params, Coaches.class).getCoachesDto();

                if (response != null) {
                    response.sort(Comparator
                            .comparing((Coaches.CoachDto coach) -> coach.getCareer() != null
                                    && coach.getCareer().stream().allMatch(c -> c.getEndDate() != null))
                            .reversed()
                            .thenComparing(coach -> {
                                if (coach.getCareer() != null) {
                                    return coach.getCareer().stream()
                                            .min(Comparator.comparing(Coaches.CoachDto.Career::getStartDate))
                                            .map(Coaches.CoachDto.Career::getStartDate)
                                            .orElse(null);
                                }
                                return null;
                            })
                    );

                    response.forEach(c -> coachService.fetchCoach(c, clubId));
                }
            } catch (IOException e) {
                throw new CoachFetchingException(COACHES_BY_TEAM_ERROR + clubId, e);
            }
        } else {
            log.warn(LIMIT_EXCEEDED_COACH, clubId);
        }
    }
}
