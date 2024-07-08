package com.footystars.foot8.api.service.scheduler;

import com.footystars.foot8.api.service.fetcher.TeamFetcher;
import com.footystars.foot8.exception.FetchLeaguesException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static com.footystars.foot8.utils.SelectedLeagues.BUNDESLIGA;
import static com.footystars.foot8.utils.SelectedLeagues.LA_LIGA;
import static com.footystars.foot8.utils.SelectedLeagues.LIGUE_1;
import static com.footystars.foot8.utils.SelectedLeagues.PREMIER_LEAGUE;
import static com.footystars.foot8.utils.SelectedLeagues.SERIE_A;
import static com.footystars.foot8.utils.SelectedLeagues.UEFA_CHAMPIONS_LEAGUE;
import static com.footystars.foot8.utils.SelectedLeagues.UEFA_EUROPA_CONFERENCE_LEAGUE;
import static com.footystars.foot8.utils.SelectedLeagues.UEFA_EUROPA_LEAGUE;

@Service
@RequiredArgsConstructor
public class TeamInfoScheduler {

    private final TeamFetcher teamFetcher;
    private static final Logger log = LoggerFactory.getLogger(TeamInfoScheduler.class);


    private static final String FETCHED = "Fetched teams from favorite leagues";
    private static final String FETCHED_ERROR = "Fetched {} failed";
//    @Scheduled(cron = "0 37 20 * * *")
    public void getPremierLeagueTeamsInfo() {
        try {
//            teamFetcher.fetchCurrentSeasonTeamsSelected();
            log.info(FETCHED);
        } catch (Exception e) {
            throw new FetchLeaguesException(FETCHED_ERROR, e);
        }
    }


}
