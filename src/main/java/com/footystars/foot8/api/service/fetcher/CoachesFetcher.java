package com.footystars.foot8.api.service.fetcher;

import com.footystars.foot8.api.model.coaches.Coaches;
import com.footystars.foot8.business.service.CoachService;
import com.footystars.foot8.business.service.SeasonService;
import com.footystars.foot8.business.service.TeamService;
import com.footystars.foot8.exception.FetchLeaguesException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.footystars.foot8.utils.LogsNames.COACHES_ERROR;
import static com.footystars.foot8.utils.ParameterName.TEAM;
import static com.footystars.foot8.utils.PathSegment.COACHS;
import static com.footystars.foot8.utils.SelectedLeagues.getFavoritesLeaguesAndCups;

@Service
@RequiredArgsConstructor
public class CoachesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final CoachService coachService;
    private final TeamService teamService;
    private final SeasonService seasonService;

    private final Logger log = LoggerFactory.getLogger(CoachesFetcher.class);


    public void fetchByLeagueId(@NotNull Long leagueId) {
        var optionalSeason = seasonService.findCurrentSeasonByLeagueId(leagueId);

        if (optionalSeason.isPresent()) {
            var season = optionalSeason.get();
            var year = season.getYear();
            var teams = teamService.getByLeagueIdAndSeason(leagueId, year);

            teams.parallelStream().forEach(t -> {
                try {
                    var clubId = t.getClubId();
                    fetchByClubId(clubId);
                } catch (FetchLeaguesException e) {
                    log.error(COACHES_ERROR, leagueId, e.getMessage());
                }
            });
        }
    }

    public void fetchByClubId(Long clubId) throws FetchLeaguesException {
        var params = new HashMap<String, String>();
        params.put(TEAM, String.valueOf(clubId));
        try {
            var response = dataFetcher.fetch(COACHS, params, Coaches.class).getResponse();
            if (response != null) {
                response.parallelStream().forEach(c -> coachService.fetchCoach(c, clubId));
                log.info("Fetched coaches for team {}", clubId);
            }
        } catch (IOException e) {
            throw new FetchLeaguesException("Error fetching coaches for team " + clubId, e);
        }
    }

    public void fetchSelectedLeagues() {
        var allIds = new ArrayList<Long>();
        var leaguesIds = getFavoritesLeaguesAndCups();
//        var clubsCupsIds = getEuropeanCups();
        allIds.addAll(leaguesIds);
//        allIds.addAll(clubsCupsIds);
        allIds.parallelStream().forEach(this::fetchByLeagueId);
    }


}
