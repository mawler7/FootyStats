package com.footystars.foot8.api.service.datafetcher;


import com.footystars.foot8.api.model.teams.statistics.TeamStatistics;
import com.footystars.foot8.buisness.service.CompetitionService;
import com.footystars.foot8.buisness.service.TeamStatsService;
import com.footystars.foot8.exception.TeamStatsException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;
import static com.footystars.foot8.utils.ParameterNames.TEAM;
import static com.footystars.foot8.utils.PathSegment.TEAMS_STATISTICS;
import static com.footystars.foot8.utils.SelectedLeagues.PREMIER_LEAGUE;

@Service
@RequiredArgsConstructor
public class TeamStatsFetcher {

    private final ApiDataFetcher dataFetcher;
    private final TeamStatsService teamStaticsService;
    private final CompetitionService competitionService;

    private final Logger logger = LoggerFactory.getLogger(TeamStatsFetcher.class);


    @NotNull
    private static Map<String, String> createParamsMap(Long id, Long league, Integer season) {
        var params = new HashMap<String, String>();
        params.put(TEAM, String.valueOf(id));
        params.put(LEAGUE, String.valueOf(league));
        params.put(SEASON, String.valueOf(season));
        return params;
    }

    @Transactional
    public void fetchTeamStatistics(@NotNull Long teamId, @NotNull Long league, @NotNull Integer season) throws TeamStatsException {
        try {
            var params = createParamsMap(teamId, league, season);
            var teamsStatsDto = dataFetcher.fetch(TEAMS_STATISTICS, params, TeamStatistics.class).getStatistic();
            if (teamsStatsDto != null) {
                teamStaticsService.fetchTeamStats(teamsStatsDto, params);
            }
        } catch (IOException e) {
            logger.error("Failed to fetch team statistics", e);
            throw new TeamStatsException(e, "Failed to fetch team statistics");
        }
    }


    @Transactional
    public void fetchSelectedLeaguesTeamsStats() {
        var leagueId = PREMIER_LEAGUE.getId();
        var year = 2023;
        var optionalCompetition = competitionService.getByLeagueAndSeasonYear(leagueId, year);
        if (optionalCompetition.isPresent()) {
            var competition = optionalCompetition.get();
            var teams = competition.getTeams();
            if (!teams.isEmpty()) {
                teams.forEach(team -> {
                    var id = team.getClub().getId();
                    fetchTeamStatistics(id, leagueId,  year);
                });
            }
        }
    }

//    @Transactional

//    public void fetchSelectedLeaguesTeamsStats() {
//        var europeansTop5LeaguesIds = SelectedLeagues.getEuropeansTop5LeaguesIds();
//        europeansTop5LeaguesIds.forEach(leagueId -> {
//            var years = seasonService.findByLeagueId(leagueId);
//            years.forEach(year -> {
//                var optionalCompetition = competitionService.getByLeagueAndSeasonYear(leagueId, year);
//                if (optionalCompetition.isPresent()) {
//                    var competition = optionalCompetition.get();
//                    Set<Team> teams = competition.getTeams();
//                    if (!teams.isEmpty()) {
//                        teams.forEach(team -> {
//                            var id = team.getClub().getId();
//                            if (id != null) {
//                                fetchTeamStatistics(id, leagueId, Long.valueOf(year));
//                            }
//                        });
//                    } else {
//                        logger.warn("No teams were found for league " + leagueId + " in season " + year);
//                    }
//                }
//            });
//        });
//    }

}



