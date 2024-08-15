package com.footystars.foot8.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footystars.foot8.api.service.fetcher.CoachesFetcher;
import com.footystars.foot8.api.service.fetcher.FixturesFetcher;
import com.footystars.foot8.api.service.fetcher.LeaguesFetcher;
import com.footystars.foot8.api.service.fetcher.OddsFetcher;
import com.footystars.foot8.api.service.fetcher.PlayersFetcher;
import com.footystars.foot8.api.service.fetcher.PredictionsFetcher;
import com.footystars.foot8.api.service.fetcher.StandingsFetcher;
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

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
    private final CoachesFetcher coachesFetcher;
    private final StandingsFetcher standingsFetcher;
    private final ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(DataFetcherScheduler.class);

    @Scheduled(cron = "0 0 12 * * *") // Codziennie o 6 rano
    public void fetchLeagues() {
        try {
            leaguesFetcher.fetchTopLeaguesAndCups();
            log.info("Fetched leagues");
        } catch (Exception e) {
            throw new FetchLeaguesException("Cannot fetch leagues ", e);
        }
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void fetchTeamsInfo() {
        try {
            teamFetcher.fetchCurrentSeasonTeamsInfo();
            log.info("Fetched teams info");
        } catch (Exception e) {
            throw new FetchLeaguesException("Cannot fetch teams info ", e);
        }
    }

    @Scheduled(cron = "0 0 12-23 * * *")
    public void fetchTeamsStats() {
        try {
        teamStatsFetcher.updateTeamsStats();
            log.info("Fetched teams stats");
        } catch (Exception e) {
            throw new FetchLeaguesException("Cannot fetch teams stats ", e);
        }
    }


    @Scheduled(cron = "0 0 23 * * *")
    public void fetchStandings() {
        try {
            standingsFetcher.fetchCurrentSeasonsStandings();
            log.info("Fetched teams info");
        } catch (Exception e) {
            throw new FetchLeaguesException("Cannot fetch teams info ", e);
        }
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void fetchCoaches() {
        try {
            coachesFetcher.fetchTopLeaguesCoaches();
            log.info(LogsNames.COACHES_FETCHED);
        } catch (Exception e) {
            throw new FixtureException("Cannot fetch coaches ", e);
        }
    }

    @Scheduled(cron = "0 0 12 * * *") // Codziennie o 10 rano
    public void fetchInjured() {
        try {
        } catch (Exception e) {
            throw new FixtureException("Cannot fetch coaches ", e);
        }
    }

    @Scheduled(cron = "0 0 12 * * *") // Codziennie o 10 rano
    public void fetchSidelined() {
        try {
        } catch (Exception e) {
            throw new FixtureException("Cannot fetch coaches ", e);
        }
    }


    @Scheduled(cron = "0 0/15 11-23 * * *") // Codziennie o 3:11 rano
    public void getFixtures() {
        try {
            fixturesFetcher.updateFixtures();

            log.info(LogsNames.ALL_FIXTURES_FETCHED);
        } catch (Exception e) {
            throw new FixtureException(LogsNames.FIXTURE_COULD_NOT_BE_FETCHED, e);
        }
    }


    @Scheduled(cron = "0 0 12-23 * * *") // Codziennie o 12 rano
    public void getPredictions() {
        try {
            predictionsFetcher.fetchTodayPredictions();
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

    @Scheduled(cron = "0 0 * * * *") // Co godzinę
    public void updateMatchData() {
        try {
            List<Fixture> matchDataList = fixtureRepository.findTodayFixturesWithDetails();
            String jsonData = objectMapper.writeValueAsString(matchDataList);
            Path filePath = Paths.get("src/main/resources/fixtures/today_matches_data.json");
            Files.writeString(filePath, jsonData);
            log.info("Today's match data updated successfully.");
        } catch (Exception e) {
            log.error("Failed to update today's match data.", e);
        }
    }
}




//        0 0 * * * * = the top of every hour of every day.

//        0 0 8-10 * * * = 8, 9 and 10 o'clock of every day.

//        0 0 6,19 * * * = 6:00 AM and 7:00 PM every day.

//        0 0/30 8-10 * * * = 8:00, 8:30, 9:00, 9:30, 10:00 and 10:30 every day.

//        0 0 9-17 * * MON-FRI= on the hour nine-to-five weekdays.

//        0 0 0 25 12 ?= every Christmas Day at midnight, no matter what weekday it is.