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

import java.util.HashMap;

import static com.footystars.foot8.utils.ParameterName.TEAM;
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
        var params = new HashMap<String, String>();
        params.put(TEAM, String.valueOf(teamId));
        var coachesDto = dataFetcher.fetch(COACHS, params, Coaches.class).getCoachesDto();
        coachesDto.forEach(coachService::fetchCoach);
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
