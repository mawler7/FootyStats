package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.exception.CountryException;
import com.footystars.foot8.exception.FixtureException;
import com.footystars.foot8.exception.LeagueException;
import com.footystars.foot8.exception.TeamInfoException;
import com.footystars.foot8.exception.TeamStatsException;
import com.footystars.foot8.exception.VenueException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class DataFetcherScheduler {

    private final CountriesFetcher countryDataFetcher;
    private final LeaguesFetcher leaguesDataFetcher;
    private final TeamInfoFetcher teamInfoDataFetcher;
    private final TeamStatsFetcher teamStatsDataFetcher;
    private final VenuesFetcher venuesDataFetcher;
    private final FixturesFetcher fixturesDataFetcher;

    private static final Logger logger = LoggerFactory.getLogger(DataFetcherScheduler.class);

//    @PostConstruct
//    void init() throws TeamInfoException, TeamStatsException, CountryException, VenueException, LeagueException, FixtureException {
//            fetchCountries();
//            fetchVenues();
//    }

    //        @Scheduled(cron = "0 0 1 */6 * *")
    public void fetchCountries() throws CountryException {
//        logger.info("Fetching countries...");
        countryDataFetcher.fetchCountries();
    }

    //    @Scheduled(cron = "0 0 1 */6 * *")
    public void fetchVenues() {
        logger.info("Fetching venues...");
        venuesDataFetcher.fetchVenuesForTopFiveEuropeanLeagues();
    }

    //        @Scheduled(cron = "0 0 1 */6  * *")
    public void fetchLeagues() {
//        logger.info("Fetching leagues...");
        leaguesDataFetcher.fetchTop5EuropeanLeagues();
    }

    //    @Scheduled(cron = "0 0 1 */6  * *")
    public void fetchTeamsInfo() {
//        logger.info("Fetching teamsInfo...");
        teamInfoDataFetcher.fetchTeamInfoFromTop5EuropeanLeagues();
    }

    public void fetchTop5TeamsStats() throws TeamStatsException {
//        logger.info("Fetching teamsStats...");
        teamStatsDataFetcher.fetchTeamStatsFromTop5EuropeanLeagues();
    }

    //    @Scheduled(cron = "0 0 1 */6  * *")
    public void fetchTop5Fixtures() {
//        logger.info("Fetching fixtures...");
        fixturesDataFetcher.fetchTop5Fixtures();
    }

}