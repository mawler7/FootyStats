package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.teams.info.response.TeamInfoDto;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.fasterxml.jackson.core.io.NumberInput.parseInt;
import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;

@Service
@RequiredArgsConstructor
public class TeamInfoService {

    private final TeamService teamService;


    @Transactional
    public void updateFromDto(@NotNull TeamInfoDto teamInfoDto, @NotNull Map<String, String> params) {
        var seasonYear = parseInt(params.get(SEASON));
        var leagueId = Long.valueOf(params.get(LEAGUE));
        var teamId = teamInfoDto.getTeamDto().getTeamId();

        if (teamId != null) {
            var team = teamService.findByTeamIdAndLeagueIdAndSeason(teamId, leagueId, seasonYear);
            if (team.isPresent()) {
                var teamEntity = team.get();
                if (teamEntity.getLeague() == null && teamEntity.getSeason() == null) {
                    teamService.updateTeamLeagueVenueAndSeasonYear(seasonYear, leagueId, teamInfoDto, teamEntity);
                }
            } else {
                teamService.createTeam(teamInfoDto, params);
            }
        }
    }


}
