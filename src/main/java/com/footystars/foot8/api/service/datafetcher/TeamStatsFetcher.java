package com.footystars.foot8.api.service.datafetcher;


import com.footystars.foot8.api.model.teams.statistics.response.TeamsStatsDto;
import com.footystars.foot8.buisness.service.TeamService;
import com.footystars.foot8.buisness.service.TeamStatsService;
import com.footystars.foot8.exception.TeamInfoException;
import com.footystars.foot8.exception.TeamStatsException;
import com.footystars.foot8.persistence.entities.teams.Team;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;
import static com.footystars.foot8.utils.ParameterNames.TEAM;
import static com.footystars.foot8.utils.PathSegment.TEAMS_STATISTICS;
import static com.footystars.foot8.utils.Seasons.getAllSeasons;
import static com.footystars.foot8.utils.SelectedLeagues.getEuropeansTop5LeaguesIds;

@Service
@RequiredArgsConstructor
public class TeamStatsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final TeamStatsService teamStaticsService;
    private final TeamService teamService;

    @NotNull
    private static Map<String, String> createParamsMap(Long id, Long league, Long season) {
        var params = new HashMap<String, String>();
        params.put(TEAM, String.valueOf(id));
        params.put(LEAGUE, String.valueOf(league));
        params.put(SEASON, String.valueOf(season));

        return params;
    }

    @Transactional
    public void fetchTeamStatistics(Long id, Long league, Long season) throws TeamStatsException {
        try {
            var params = createParamsMap(id, league, season);
            var teamsStatsDto = getTeamsStatsDto(params).getTeamStatsDto();
            if (teamsStatsDto != null) {
                teamStaticsService.updateFromDto(teamsStatsDto);
            }
        } catch (IOException e) {
            throw new TeamStatsException(e, "Failed to fetch team statistics");
        }
    }

    private TeamsStatsDto getTeamsStatsDto(Map<String, String> params) throws IOException {
        try {
            return dataFetcher.fetch(TEAMS_STATISTICS, params, TeamsStatsDto.class);
        } catch (Exception e) {
            throw new TeamStatsException(e, "Could not fetch teams statistics from API: " + e.getMessage());
        }
    }

    @Transactional
    public void fetchTeamStatsFromTop5EuropeanLeagues() {
        var allSeasons = getAllSeasons();
        var seasons = allSeasons.stream().filter(s -> s < 2024).toList();
        var top5LeaguesIds = getEuropeansTop5LeaguesIds();

        seasons.forEach(s -> top5LeaguesIds.forEach(l -> {
            var teams = teamService.findTeamsByLeagueIdAndSeason(l, s);
            teams.forEach(t -> {
                var teamId = t.getTeamId();
                try {
                    fetchTeamStatistics(teamId, l, Long.valueOf(s));
                } catch (Exception e) {
                    throw new TeamStatsException(e, "Could not get team statistics");
                }
            });
        }));
    }
//        seasons.forEach(season -> top5LeaguesIds.parallelStream().forEach(leagueId -> {
//            try {
//                var teams = teamService.findTeamsByLeagueIdAndSeason(leagueId, season);
//                teams.parallelStream().forEach(team -> {
//                    var teamId = team.getTeamId();
//                    try {
//                        fetchTeamStatistics(teamId, leagueId, Long.valueOf(season));
//                    } catch (TeamInfoException e) {
//                        handleException(e);
//                    }
//                });
//            } catch (TeamInfoException e) {
//                handleException(e);
//            }
//        }));
//    }


}




