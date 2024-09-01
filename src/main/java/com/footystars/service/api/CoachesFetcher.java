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
import java.util.HashMap;

import static com.footystars.utils.LogsNames.COACHES_ERROR;
import static com.footystars.utils.ParameterName.TEAM;
import static com.footystars.utils.PathSegment.COACHS;
import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

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

    @Async
    public void fetchTopLeaguesCoaches() {
        getTopLeaguesIds().forEach(this::fetchCoachesByLeagueIdInCurrentSeason);
        log.info("Fetches coaches for all leagues");
    }

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
            log.info("Fetched coaches for leagueId: {}", leagueId);
        }
    }

    public void fetchCoachByClubId(Long clubId) throws CoachFetchingException {
        if (bucket.tryConsume(1)) {
            var params = new HashMap<String, String>();
            params.put(TEAM, String.valueOf(clubId));

            try {
                var response = dataFetcher.fetch(COACHS, params, Coaches.class).getCoachesDto();

                if (response != null) {
                    response.forEach(c -> coachService.fetchCoach(c, clubId));
                }
            } catch (IOException e) {
                throw new CoachFetchingException("Error fetching coaches for team " + clubId, e);
            }
        } else {
            log.warn("Request limit exceeded for teamId: {}", clubId);
        }
    }

}


