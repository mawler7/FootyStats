package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.teams.Teams;
import com.footystars.foot8.buisness.service.ClubService;
import com.footystars.foot8.buisness.service.SeasonService;
import com.footystars.foot8.exception.TeamInfoException;
import com.footystars.foot8.utils.SelectedLeagues;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;
import static com.footystars.foot8.utils.PathSegment.TEAMS_INFORMATION;


@Service
@RequiredArgsConstructor
public class TeamFetcher {

    private final ApiDataFetcher dataFetcher;
    private final ClubService clubService;
    private final SeasonService seasonService;

    private final Log logger = LogFactory.getLog(TeamFetcher.class);

    @NotNull
    private static Map<String, String> createParamsMap(Long league, Integer season) {
        var params = new HashMap<String, String>();
        params.put(LEAGUE, String.valueOf(league));
        params.put(SEASON, String.valueOf(season));
        return params;
    }


    @Transactional
    public void fetchTeamInfoByLeagueAndSeason(@NotNull Long league, @NotNull Integer season) {
        var params = createParamsMap(league, season);
        try {
            var teamsApi = dataFetcher.fetch(TEAMS_INFORMATION, params, Teams.class).getTeamsApi();
            teamsApi.forEach(t -> clubService.fetchClub(t, params));
        } catch (IOException e) {
            logger.error("Fetching teams from league " + league + " and season " + season + " failed", e);
            throw new TeamInfoException("Failed to fetch teams for league " + league + " and season " + season, e);
        }
    }

    @Transactional
    public void fetchSelectedLeaguesTeamsInfo() {
        var europeansTop5LeaguesIds = SelectedLeagues.getEuropeansTop5LeaguesIds();
        europeansTop5LeaguesIds.forEach(id -> {
            var years = seasonService.findByLeagueId(id);
            years.forEach(seasonYear -> fetchTeamInfoByLeagueAndSeason(id, seasonYear));
        });
    }
}
