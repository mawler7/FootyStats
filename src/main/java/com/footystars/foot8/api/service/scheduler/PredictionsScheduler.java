package com.footystars.foot8.api.service.scheduler;

import com.footystars.foot8.api.service.fetcher.PredictionsFetcher;
import com.footystars.foot8.exception.FixtureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PredictionsScheduler {

    private final PredictionsFetcher predictionsFetcher;
    private static final Logger log = LoggerFactory.getLogger(PredictionsScheduler.class);


    private static final String FETCHED = "Fetched all fixtures";
    private static final String FETCHED_ERROR = "Fetched {} failed";

//    @Scheduled(cron = "0 56 20 * * *")
    public void getFixturesPredictions() {
        try {
//            predictionsFetcher.fetchCurrentSeasonPredictions();
            log.info(FETCHED);
        } catch (Exception e) {
            throw new FixtureException(e, FETCHED_ERROR);
        }
    }

}
