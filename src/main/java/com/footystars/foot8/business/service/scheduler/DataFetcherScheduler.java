package com.footystars.foot8.business.service.scheduler;

import com.footystars.foot8.api.service.fetcher.CoachesFetcher;
import com.footystars.foot8.api.service.fetcher.FixturesFetcher;
import com.footystars.foot8.api.service.fetcher.LeaguesFetcher;
import com.footystars.foot8.api.service.fetcher.OddsFetcher;
import com.footystars.foot8.api.service.fetcher.PlayersFetcher;
import com.footystars.foot8.api.service.fetcher.PredictionsFetcher;
import com.footystars.foot8.api.service.fetcher.TeamFetcher;
import com.footystars.foot8.api.service.fetcher.TeamStatsFetcher;
import com.footystars.foot8.business.model.entity.Fixture;
import com.footystars.foot8.exception.FetchLeaguesException;
import com.footystars.foot8.exception.FixtureException;
import com.footystars.foot8.exception.PredictionFetchException;
import com.footystars.foot8.repository.FixtureRepository;
import com.footystars.foot8.utils.LogsNames;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataFetcherScheduler {
    private final FixtureRepository fixtureRepository;
    private final FixturesFetcher fixturesFetcher;
    private final LeaguesFetcher leaguesFetcher;
    private final TeamFetcher teamFetcher;
    private final TeamStatsFetcher teamStatsFetcher;
    private final PredictionsFetcher predictionsFetcher;
    private final OddsFetcher oddsFetcher;
    private final PlayersFetcher playersFetcher;
    private final CoachesFetcher coachesFetcher;

    private static final Logger log = LoggerFactory.getLogger(DataFetcherScheduler.class);


    @Scheduled(cron = "0 0 * * * *") // Co godzinę
    @Transactional
    public void checkAndFetchFixtures() {
        var now = ZonedDateTime.now();
        var startOfDay = now.toLocalDate().atStartOfDay(now.getZone());
        var endOfDay = now.toLocalDate().plusDays(1).atStartOfDay(now.getZone()).minusSeconds(1);

        var ongoingFixtures = fixtureRepository.findNotStartedByDate(startOfDay, endOfDay).stream()
                .filter(fixture -> fixture.getDate().isBefore(now))
                .toList();

        var leagueIds = ongoingFixtures.stream()
                .map(Fixture::getId)
                .collect(Collectors.toSet());

        leagueIds.forEach(fixturesFetcher::fetchCurrentSeasonsFixturesByLeagueId);
    }

    @Scheduled(cron = "0 0 15-23 * * *") // Codziennie o 3:11 rano
    public void getFixtures() {
        try {
            fixturesFetcher.fetchTopLeaguesAndCupsCurrentSeasonsNotFinishedFixtures();
            log.info(LogsNames.ALL_FIXTURES_FETCHED);
        } catch (Exception e) {
            throw new FixtureException(LogsNames.FIXTURE_COULD_NOT_BE_FETCHED, e);
        }
    }
    @Scheduled(cron = "0 0/35 18 * * *") // Codziennie o 6 rano
    @Transactional
    public void fetchLeagues() {
        try {
            leaguesFetcher.fetchTopLeaguesAndCups();
            log.info("Fetched leagues");
        } catch (Exception e) {
            throw new FetchLeaguesException("Cannot fetch leagues ", e);
        }

    }

    @Scheduled(cron = "0 0 19 * * *") // Codziennie o 6 rano
    @Transactional
    public void fetchTeamsInfo() {
        try {
            teamFetcher.fetchCurrentSeasonTeamsInfo();
            log.info("Fetched teams info");
        } catch (Exception e) {
            throw new FetchLeaguesException("Cannot fetch teams info ", e);
        }

    }

    @Scheduled(cron = "0 0/30 19 * * *") // Codziennie o 8 rano
    @Transactional
    public void fetchTeamsStats() {
        try {
        teamStatsFetcher.fetchCurrentSeasonsTeamStats();
            log.info("Fetched teams stats");
        } catch (Exception e) {
            throw new FetchLeaguesException("Cannot fetch teams stats ", e);
        }
    }

    @Scheduled(cron = "0 0/30 19 * * *") // Codziennie o 9 rano
    public void getCoaches() {
        try {
            coachesFetcher.fetchTopLeaguesCoaches();
            log.info(LogsNames.COACHES_FETCHED);
        } catch (Exception e) {
            throw new FixtureException("Cannot fetch coaches ", e);
        }
    }

    @Scheduled(cron = "0 0 20 * * *") // Codziennie o 12 rano
    public void getFixturesPredictions() {
        try {
            predictionsFetcher.fetchCurrentSeasonPredictions();
            log.info("Predictions fetched");
        } catch (Exception e) {
            throw new PredictionFetchException(e.getMessage());
        }
    }

    @Scheduled(cron = "0 0/30 20 * * *") // Codziennie o 12 rano
    public void getOdds() {
        try {
            oddsFetcher.fetchByAllLeagues();
            log.info("Predictions fetched");
        } catch (Exception e) {
            throw new PredictionFetchException(e.getMessage());
        }
    }


}


//        0 0 * * * * = the top of every hour of every day.

//        0 0 8-10 * * * = 8, 9 and 10 o'clock of every day.

//        0 0 6,19 * * * = 6:00 AM and 7:00 PM every day.

//        0 0/30 8-10 * * * = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.

//        0 0 9-17 * * MON-FRI= on the hour nine-to-five weekdays.

//        0 0 0 25 12 ?= every Christmas Day at midnight, no matter what weekday it is.