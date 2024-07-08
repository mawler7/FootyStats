package com.footystars.foot8.api.service.scheduler;
import com.footystars.foot8.api.service.fetcher.EventsFetcher;
import com.footystars.foot8.api.service.fetcher.LineupsFetcher;
import com.footystars.foot8.api.service.fetcher.FixturesFetcher;
import com.footystars.foot8.exception.FixtureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FixturesScheduler {

    private final FixturesFetcher fixturesFetcher;
    private final LineupsFetcher fixtureLineupsFetcher;
    private final EventsFetcher fixtureEventsFetcher;

    private static final Logger log = LoggerFactory.getLogger(FixturesScheduler.class);

    private static final String FETCHED = "Fetched all fixtures";
    private static final String FETCHED_ERROR = "Fetched {} failed";
//    @Scheduled(cron = "0 44 20 * * *")
    public void getFixtures() {
        try {
            fixturesFetcher.fetchCurrentSeasonsFixtures();
            log.info(FETCHED);
        } catch (Exception e) {
            throw new FixtureException(e, FETCHED_ERROR);
        }
    }

//    @Scheduled(cron = "0 48 20 * * *")
    public void getFixturesLineups() {
        try {
            fixtureLineupsFetcher.fetchFavorites();
            log.info(FETCHED);
        } catch (Exception e) {
            throw new FixtureException(e, FETCHED_ERROR);
        }
    }

//    @Scheduled(cron = "0 52 20 * * *")
    public void getFixturesEvents() {
        try {
            fixtureEventsFetcher.fetchEventsForCurrentSeason();
            log.info(FETCHED);
        } catch (Exception e) {
            throw new FixtureException(e, FETCHED_ERROR);
        }
    }

}
