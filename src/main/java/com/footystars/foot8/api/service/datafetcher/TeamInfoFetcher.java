package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.teams.Teams;
import com.footystars.foot8.buisness.service.LeagueSeasonService;
import com.footystars.foot8.buisness.service.TeamInfoService;
import com.footystars.foot8.exception.TeamInfoException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;
import static com.footystars.foot8.utils.PathSegment.TEAMS_INFORMATION;
import static com.footystars.foot8.utils.SelectedLeagues.PREMIER_LEAGUE;


@Service
@RequiredArgsConstructor
public class TeamInfoFetcher {

    private final ApiDataFetcher dataFetcher;
    private final TeamInfoService teamInfoService;
    private final LeagueSeasonService leagueSeasonService;

    @NotNull
    private static Map<String, String> createParamsMap(Long league, Long season) {
        var params = new HashMap<String, String>();
        params.put(LEAGUE, String.valueOf(league));
        params.put(SEASON, String.valueOf(season));
        return params;
    }

    @Transactional
    public void fetchTeamInfoByLeagueAndSeason(@NotNull Long league, @NotNull Long season) throws TeamInfoException {
        try {
            var params = createParamsMap(league, season);
            var teamInfoDto = dataFetcher.fetch(TEAMS_INFORMATION, params, Teams.class).getTeamList();
            teamInfoDto.forEach(t -> teamInfoService.fetchResponse(t, params));

        } catch (IOException e) {
            throw new TeamInfoException(e, "Failed to fetch team information");
        }
    }

    @Transactional
    public void fetchSelectedLeaguesTeamsInfo() {

            var leagueSeasons = leagueSeasonService.getLeagueSeasonsYears(PREMIER_LEAGUE.getId());
            leagueSeasons.forEach(season -> {
                try {
                    fetchTeamInfoByLeagueAndSeason(PREMIER_LEAGUE.getId(), Long.valueOf(season));
                } catch (TeamInfoException e) {
                    e.printStackTrace();
                }
            });


    }
}
