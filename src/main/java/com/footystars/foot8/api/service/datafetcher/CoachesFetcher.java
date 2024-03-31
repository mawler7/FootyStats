package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.coaches.Coaches;
import com.footystars.foot8.buisness.service.CoachService;
import com.footystars.foot8.buisness.service.CompetitionService;
import com.footystars.foot8.exception.FetchLeaguesException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;

import static com.footystars.foot8.utils.ParameterNames.TEAM;
import static com.footystars.foot8.utils.PathSegment.COACHS;
import static com.footystars.foot8.utils.SelectedLeagues.PREMIER_LEAGUE;

@Service
@RequiredArgsConstructor
public class CoachesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final CoachService coachService;
    private final CompetitionService competitionService;

    private final Logger log = LoggerFactory.getLogger(CoachesFetcher.class);


    @Transactional
    public void fetchByTeamId(Long teamId) {
        try {
            var params = new HashMap<String, String>();
            params.put(TEAM, String.valueOf(teamId));
            var coachesDto = dataFetcher.fetch(COACHS, params, Coaches.class).getCoachesDto();
            coachesDto.forEach(coachService::fetchCoach);
        } catch (IOException e) {
            throw new FetchLeaguesException("Could not fetch coach for teamId: " + teamId, e);
        }
    }

    @Transactional
    public void fetchSelected() {
        var leagueId = PREMIER_LEAGUE.getId();
        var year = 2023;
        var optionalCompetition = competitionService.getByLeagueAndSeasonYear(leagueId, year);
        optionalCompetition.ifPresent(competition -> {
            var teams = competition.getTeams();
            teams.forEach(team -> {
                var clubId = team.getClub().getId();
                try {
                    fetchByTeamId(clubId);
                } catch (FetchLeaguesException e) {
                    log.error("Error fetching coach data for teamId: {}", clubId, e);
                }
            });
        });
    }
}
//    @Transactional
//    public void fetchSelected() {
//        List<Long> europeansTop5LeaguesIds = SelectedLeagues.getEuropeansTop5LeaguesIds();
//        europeansTop5LeaguesIds.forEach(leagueId -> {
//            var years = seasonService.findByLeagueId(leagueId);
//            years.forEach(year -> {
//                Optional<Competition> optionalCompetition = competitionService.getByLeagueAndSeasonYear(leagueId, year);
//                if (optionalCompetition.isPresent()) {
//                    var competition = optionalCompetition.get();
//                    var teams = competition.getTeams();
//                    teams.forEach(team -> {
//                        var teamId = team.getId();
//                        try {
//                            fetchByTeamId(teamId);
//                        } catch (FetchLeaguesException e) {
//                            e.printStackTrace();
//                        }
//                    });
//                }
//            });
//        });
//    }
