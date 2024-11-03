package com.footystars.service.scheduler;

import com.footystars.exception.CoachFetchingException;
import com.footystars.exception.FetchLeaguesException;
import com.footystars.exception.PredictionsException;
import com.footystars.service.api.CoachesFetcher;
import com.footystars.service.api.FixturesFetcher;
import com.footystars.service.api.LeaguesFetcher;
import com.footystars.service.api.OddsFetcher;
import com.footystars.service.api.PredictionsFetcher;
import com.footystars.service.api.StandingsFetcher;
import com.footystars.service.api.TeamFetcher;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.footystars.utils.LogsNames.COACHES_FETCHED;
import static com.footystars.utils.LogsNames.COACHES_FETCH_ERROR;
import static com.footystars.utils.LogsNames.LEAGUES_FETCHED;
import static com.footystars.utils.LogsNames.LEAGUES_FETCHING_ERROR;
import static com.footystars.utils.LogsNames.PREDICTIONS_FETCHED;
import static com.footystars.utils.LogsNames.TEAMS_INFO_ERROR;
import static com.footystars.utils.LogsNames.TEAMS_INFO_FETCHED;

@Service
@RequiredArgsConstructor
public class DataFetcherScheduler {

    private final LeaguesFetcher leaguesFetcher;
    private final FixturesFetcher fixturesFetcher;
    private final TeamFetcher teamFetcher;
    private final PredictionsFetcher predictionsFetcher;
    private final OddsFetcher oddsFetcher;
    private final CoachesFetcher coachesFetcher;
    private final StandingsFetcher standingsFetcher;

    private static final Logger log = LoggerFactory.getLogger(DataFetcherScheduler.class);

    @EventListener(ApplicationReadyEvent.class)
    public void runAtStartup() {
        updateFixtures();
        getPredictions();
        getOdds();
    }

    @Scheduled(cron = "0 * * * * *")
    public void updateLiveFixtures() {
        try {
            fixturesFetcher.updateLiveFixtures();

        } catch (Exception e) {
            throw new FetchLeaguesException(LEAGUES_FETCHING_ERROR, e);
        }
    }

    @Scheduled(cron = "0 0,15,30,45 11-23 * * *")
    public void updateFixtures() {
        try {
            fixturesFetcher.fetchTodayFixtures();
        } catch (Exception e) {
            throw new FetchLeaguesException(LEAGUES_FETCHING_ERROR, e);
        }
    }

    @Scheduled(cron = "0 0 0 * * 5")
    public void fetchLeagues() {
        try {
            leaguesFetcher.fetchTopLeaguesAndCups();
            log.info(LEAGUES_FETCHED);
        } catch (Exception e) {
            throw new FetchLeaguesException(LEAGUES_FETCHING_ERROR, e);
        }
    }

    @Scheduled(cron = "0 0 10 * * 5")
    public void fetchTeamsInfo() {
        try {
            teamFetcher.fetchCurrentSeasonTeamsInfo();
            log.info(TEAMS_INFO_FETCHED);
        } catch (Exception e) {
            throw new FetchLeaguesException(TEAMS_INFO_ERROR, e);
        }
    }

    @Scheduled(cron = "0 0 11-23 * * *")
    public void fetchStandings() {
        try {
            standingsFetcher.fetchCurrentSeasonsStandings();
        } catch (Exception e) {
            throw new FetchLeaguesException("Cannot fetch teams info ", e);
        }
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void fetchCoaches() {
        try {
            coachesFetcher.fetchTopLeaguesCoaches();
            log.info(COACHES_FETCHED);
        } catch (Exception e) {
            throw new CoachFetchingException(COACHES_FETCH_ERROR, e);
        }
    }

    @Scheduled(cron = "0 0 12-23 * * *")
    public void getPredictions() {
        try {
            predictionsFetcher.fetchUpcomingPredictions();
            log.info(PREDICTIONS_FETCHED);
        } catch (Exception e) {
            throw new PredictionsException(e.getMessage());
        }
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void getOdds() {
        try {
            oddsFetcher.fetchTodayOdds();
            log.info("Odds fetched");
        } catch (Exception e) {
            throw new PredictionsException(e.getMessage());
        }
    }

}
