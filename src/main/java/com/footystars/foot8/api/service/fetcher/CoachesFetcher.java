package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.coaches.Coaches;
import com.footystars.foot8.business.service.CoachService;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.business.service.TeamService;
import com.footystars.foot8.exception.FetchLeaguesException;
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
import java.util.HashMap;

import static com.footystars.foot8.utils.LogsNames.COACHES_ERROR;
import static com.footystars.foot8.utils.ParameterName.TEAM;
import static com.footystars.foot8.utils.PathSegment.COACHS;
import static com.footystars.foot8.utils.TopLeagues.getTopLeaguesIds;

@Service
@RequiredArgsConstructor
public class CoachesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final CoachService coachService;
    private final TeamService teamService;
    private final SeasonService seasonService;

    private final Logger log = LoggerFactory.getLogger(CoachesFetcher.class);


    private final Bucket bucket = Bucket4j.builder()
            .addLimit(Bandwidth.classic(450, Refill.greedy(450, Duration.ofMinutes(1))))
            .build();

    @Async
    public void fetchTopLeaguesCoaches() {
        var ids = getTopLeaguesIds();
        ids.forEach(this::fetchCoachesByLeagueId);
    }
    public void fetchCoachesByLeagueId(@NotNull Long leagueId) {
        var optionalSeason = seasonService.findCurrentSeasonByLeagueId(leagueId);
        if (optionalSeason.isPresent()) {
            var season = optionalSeason.get();
            var year = season.getYear();
            var teams = teamService.getByLeagueIdAndSeason(leagueId, year);

            teams.forEach(t -> {
                try {
                    var clubId = t.getClubId();
                    fetchCoachByClubId(clubId);
                } catch (FetchLeaguesException e) {
                    log.error(COACHES_ERROR, leagueId, e.getMessage());
                }
            });
        }
    }

    @Async
    public void fetchCoachByClubId(Long clubId) throws FetchLeaguesException {
        if (bucket.tryConsume(1)) {
            var params = new HashMap<String, String>();
            params.put(TEAM, String.valueOf(clubId));
            try {
                var response = dataFetcher.fetch(COACHS, params, Coaches.class).getResponse();
                if (response != null) {
                    response.forEach(c -> coachService.fetchCoach(c, clubId));
                    log.info("Fetched coaches for team {}", clubId);
                }
            } catch (IOException e) {
                throw new FetchLeaguesException("Error fetching coaches for team " + clubId, e);
            }
        } else {
            log.warn("Request limit exceeded for teamId: {}", clubId);
        }
    }

}


