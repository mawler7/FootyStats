package com.footystars.foot8.api.service.datafetcher;

import com.footystars.foot8.api.model.coaches.Coaches;
import com.footystars.foot8.buisness.service.CoachService;
import com.footystars.foot8.buisness.service.LeagueSeasonService;
import com.footystars.foot8.buisness.service.TeamSeasonService;
import com.footystars.foot8.exception.FetchLeaguesException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;

import static com.footystars.foot8.utils.ParameterNames.COACH;
import static com.footystars.foot8.utils.ParameterNames.ID;
import static com.footystars.foot8.utils.ParameterNames.TEAM;
import static com.footystars.foot8.utils.PathSegment.COACHS;
import static com.footystars.foot8.utils.SelectedLeagues.PREMIER_LEAGUE;

@Service
@RequiredArgsConstructor
public class CoachesFetcher {

    private final ApiDataFetcher dataFetcher;
    private final CoachService coachService;
    private final LeagueSeasonService leagueSeasonService;
    private final TeamSeasonService teamSeasonService;

    @Async
    @Transactional
    public void fetchByTeamId(Long teamId)  {
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
    public void fetchByCoachId(Long coachId)  {
        try {
            var params = new HashMap<String, String>();
            params.put(ID, String.valueOf(coachId));
            var coachesDto = dataFetcher.fetch(COACH, params, Coaches.class).getCoachesDto();
            coachesDto.forEach(coachService::fetchCoach);
        } catch (IOException e) {
            throw new FetchLeaguesException("Could not fetch coach with id: " + coachId, e);
        }
    }

@Transactional
    public void fetchSelected()  {
        var leagueId = PREMIER_LEAGUE.getId();
    var seasonsYears = leagueSeasonService.getLeagueSeasonsYears(leagueId);

    seasonsYears.forEach(season -> {
        var teamSeasons = teamSeasonService.findTeamIdsByLeagueIdAndSeason(leagueId, season);
        teamSeasons.forEach(teamSeason -> {
            var teamId = teamSeason.getTeam().getId();
            try {
                fetchByTeamId(teamId);
            } catch (FetchLeaguesException e) {
                e.printStackTrace();
            }
        });
    });

}

}
