package com.footystars.foot8.api.service.datafetcher;


import com.footystars.foot8.api.model.teams.statistics.TeamStatistics;
import com.footystars.foot8.buisness.service.LeagueSeasonService;
import com.footystars.foot8.buisness.service.TeamStatsService;
import com.footystars.foot8.exception.TeamStatsException;
import com.footystars.foot8.utils.SelectedLeagues;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;
import static com.footystars.foot8.utils.ParameterNames.TEAM;
import static com.footystars.foot8.utils.PathSegment.TEAMS_STATISTICS;

@Service
@RequiredArgsConstructor
public class TeamStatsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final TeamStatsService teamStaticsService;
    private final LeagueSeasonService leagueSeasonService;

    @NotNull
    private static Map<String, String> createParamsMap(Long id, Long league, Long season) {
        var params = new HashMap<String, String>();
        params.put(TEAM, String.valueOf(id));
        params.put(LEAGUE, String.valueOf(league));
        params.put(SEASON, String.valueOf(season));

        return params;
    }

    @Transactional(rollbackFor = TeamStatsException.class)

    public void fetchTeamStatistics(@NotNull Long teamId, @NotNull Long league, @NotNull Long season) throws TeamStatsException {
        try {
            var params = createParamsMap(teamId, league, season);
             var teamsStatsDto = dataFetcher.fetch(TEAMS_STATISTICS, params, TeamStatistics.class).getStatistic();
                teamStaticsService.fetchTeamStats(teamsStatsDto);
        } catch (IOException e) {
            throw new TeamStatsException(e, "Failed to fetch team statistics");
        }
    }


    @Transactional
    public void fetchSelectedLeaguesTeamsStats() {
        var leagueId = SelectedLeagues.PREMIER_LEAGUE.getId();
        var seasonsYears = leagueSeasonService.getLeagueSeasonsYears(leagueId);

        seasonsYears.forEach(year -> {

            var optionalLeagueSeason = leagueSeasonService.getByLeagueIdAndSeason(leagueId, year);
            optionalLeagueSeason.ifPresent(leagueSeason -> {
                var teamSeasons = leagueSeason.getTeamSeasons();
                teamSeasons.forEach(teamSeason -> {
                    var teamId = teamSeason.getTeam().getId();
                    if (teamId != null) {
                        try {
                            fetchTeamStatistics(teamId, leagueId, Long.valueOf(year));
                        } catch (TeamStatsException e) {
                            e.printStackTrace();
                        }
                    }
                });
            });


        });
    }
}






