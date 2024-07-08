package com.footystars.foot8.api.service.scheduler;

import com.footystars.foot8.api.service.fetcher.StandingsFetcher;
import com.footystars.foot8.exception.FetchLeaguesException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StandingsScheduler {

    private final StandingsFetcher standingsFetcher;
    private static final Logger log = LoggerFactory.getLogger(StandingsScheduler.class);

//    @Scheduled(cron = "0 36 20 * * *")
    public void getTeamsInfo() {
        try {
            standingsFetcher.fetchCurrentSeasonsStandings();
            log.info("Fetched standings");
        } catch (Exception e) {
            throw new FetchLeaguesException("Cannot fetch standings ", e);
        }
    }

}
