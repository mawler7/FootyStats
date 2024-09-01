package com.footystars.service.scheduler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footystars.service.api.CoachesFetcher;
import com.footystars.service.api.FixturesFetcher;
import com.footystars.service.api.OddsFetcher;
import com.footystars.service.api.PredictionsFetcher;
import com.footystars.service.api.StandingsFetcher;
import com.footystars.service.api.TeamFetcher;
import com.footystars.service.api.TeamStatsFetcher;
import com.footystars.persistence.entity.Fixture;
import com.footystars.exception.FetchLeaguesException;
import com.footystars.exception.FixtureException;
import com.footystars.exception.PredictionFetchException;
import com.footystars.persistence.repository.FixtureRepository;
import com.footystars.utils.LogsNames;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataFetcherScheduler {
    private final FixtureRepository fixtureRepository;
    private final FixturesFetcher fixturesFetcher;
    private final TeamFetcher teamFetcher;
    private final TeamStatsFetcher teamStatsFetcher;
    private final PredictionsFetcher predictionsFetcher;
    private final OddsFetcher oddsFetcher;
    private final CoachesFetcher coachesFetcher;
    private final StandingsFetcher standingsFetcher;
    private final ObjectMapper objectMapper;

    private static final Logger log = LoggerFactory.getLogger(DataFetcherScheduler.class);

    @Scheduled(cron = "0 0 12 * * *")
    public void fetchTeamsInfo() {
//        try {
//            teamFetcher.fetchCurrentSeasonTeamsInfo();
//            log.info("Fetched teams info");
//        } catch (Exception e) {
//            throw new FetchLeaguesException("Cannot fetch teams info ", e);
//        }
    }

    @Scheduled(cron = "0 0 12-23 * * *")
    public void fetchTeamsStats() {
//        try {
//        teamStatsFetcher.updateTeamsStats();
//            log.info("Fetched teams stats");
//        } catch (Exception e) {
//            throw new FetchLeaguesException("Cannot fetch teams stats ", e);
//        }
    }


//    @Scheduled(cron = "0 0 23 * * *")
//    public void fetchStandings() {
//        try {
//            standingsFetcher.fetchCurrentSeasonsStandings();
//        } catch (Exception e) {
//            throw new FetchLeaguesException("Cannot fetch teams info ", e);
//        }
//    }

    @Scheduled(cron = "0 0 12 * * *")
    public void fetchCoaches() {
//        try {
//            coachesFetcher.fetchTopLeaguesCoaches();
//            log.info(LogsNames.COACHES_FETCHED);
//        } catch (Exception e) {
//            throw new FixtureException("Cannot fetch coaches ", e);
//        }
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void fetchInjured() {
//        try {
//        } catch (Exception e) {
//            throw new FixtureException("Cannot fetch coaches ", e);
//        }
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void fetchSidelined() {
//        try {
//        } catch (Exception e) {
//            throw new FixtureException("Cannot fetch coaches ", e);
//        }
    }


    @Scheduled(cron = "0 0/15 11-23 * * *")
    public void getFixtures() {
//        try {
//            fixturesFetcher.updateFixtures();

//            log.info(LogsNames.ALL_FIXTURES_FETCHED);
//        } catch (Exception e) {
//            throw new FixtureException(LogsNames.FIXTURE_COULD_NOT_BE_FETCHED, e);
//        }
    }

//
//    @Scheduled(cron = "0 0 12-23 * * *") // Codziennie o 12 rano
//    public void getPredictions() {
//        try {
//            predictionsFetcher.fetchTodayPredictions();
//            log.info("Predictions fetched");
//        } catch (Exception e) {
//            throw new PredictionFetchException(e.getMessage());
//        }
//    }


    @Scheduled(cron = "0 0/30 20 * * *")
    public void getOdds() {
//        try {
//            oddsFetcher.fetchByAllLeagues();
//            log.info("Predictions fetched");
//        } catch (Exception e) {
//            throw new PredictionFetchException(e.getMessage());
//        }
    }

    @Scheduled(cron = "0 0 * * * *") // Co godzinę
    public void updateMatchData() {
//        try {
//            List<Fixture> matchDataList = fixtureRepository.findTodayFixturesWithDetails();
//            String jsonData = objectMapper.writeValueAsString(matchDataList);
//            Path filePath = Paths.get("src/main/resources/fixtures/today_matches_data.json");
//            Files.writeString(filePath, jsonData);
//            log.info("Today's match data updated successfully.");
//        } catch (Exception e) {
//            log.error("Failed to update today's match data.", e);
//        }
    }
}
