package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.teams.info.response.TeamInfoResponse;
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
import static com.footystars.foot8.utils.Seasons.getAllSeasons;
import static com.footystars.foot8.utils.SelectedLeagues.getEuropeansTop5LeaguesIds;

@Service
@RequiredArgsConstructor
public class TeamInfoFetcher {

    private final ApiDataFetcher dataFetcher;
    private final TeamInfoService teamInfoService;

    @NotNull
    private static Map<String, String> createParamsMap(Long league, Long season) {
        var params = new HashMap<String, String>();
        params.put(LEAGUE, String.valueOf(league));
        params.put(SEASON, String.valueOf(season));
        return params;
    }

    @Transactional
    public void fetchTeamInfo(@NotNull Long league, @NotNull Long season) throws TeamInfoException {
        try {
            var params = createParamsMap(league, season);
            var teamInfoDto = dataFetcher.fetch(TEAMS_INFORMATION, params, TeamInfoResponse.class).getResponse();
            teamInfoDto.forEach(t -> teamInfoService.updateFromDto(t, params));

        } catch (IOException e) {
            throw new TeamInfoException(e, "Failed to fetch team information");
        }
    }

    @Transactional
    public void fetchTeamInfoFromTop5EuropeanLeagues() {
        var allSeasons = getAllSeasons();
        var top5LeaguesIds = getEuropeansTop5LeaguesIds();
        allSeasons.forEach(s -> {
            try {
                top5LeaguesIds.forEach(l -> fetchTeamInfo(l, Long.valueOf(s)));
            } catch (TeamInfoException e) {
                e.printStackTrace();
            }
        });
    }

}


