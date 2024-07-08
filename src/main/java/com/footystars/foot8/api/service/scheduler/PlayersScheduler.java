package com.footystars.foot8.api.service.scheduler;

import com.footystars.foot8.api.service.fetcher.PlayersFetcher;
import com.footystars.foot8.exception.FixtureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlayersScheduler {

    private final PlayersFetcher playersFetcher;

    private static final Logger log = LoggerFactory.getLogger(PlayersScheduler.class);
    private static final String FETCHED = "Fetched all fixtures";
    private static final String FETCHED_ERROR = "Fetched {} failed";

//    @Scheduled(cron = "0 59 20 * * *")
//    public void getPlayers() {
//        try {
//            playersFetcher.fetchPlayersBySelectedLeagues();
//            log.info(FETCHED);
//        } catch (Exception e) {
//            throw new FixtureException(e, FETCHED_ERROR);
//        }
//    }

}
