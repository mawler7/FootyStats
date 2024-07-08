package com.footystars.foot8.api.service.scheduler;

import com.footystars.foot8.api.service.fetcher.LeaguesFetcher;
import com.footystars.foot8.exception.FetchLeaguesException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LeaguesScheduler {

    private final LeaguesFetcher leaguesFetcher;
    private static final Logger log = LoggerFactory.getLogger(LeaguesScheduler.class);

//    @Scheduled(cron = "0 35 20 * * *")
    public void getLeagues() {
        try {
            leaguesFetcher.fetchSelectedLeagues();
            log.info("Fetched leagues");
        } catch (Exception e) {
            throw new FetchLeaguesException("Cannot fetch leagues ", e);
        }
    }

}
