package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.teams.info.response.TeamInfoDto;
import com.footystars.foot8.persistence.entities.leagues.League;
import com.footystars.foot8.persistence.entities.teams.Team;
import com.footystars.foot8.persistence.entities.teams.TeamMapper;
import com.footystars.foot8.persistence.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;

@Service
@RequiredArgsConstructor
public class  TeamService {
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final LeagueService leagueService;


    public void createTeam(@NotNull TeamInfoDto teamInfoDto, @NotNull Map<String, String> params) {
        var seasonYear = params.get(SEASON);
        var leagueId = Long.valueOf(params.get(LEAGUE));
        var teamDto = teamInfoDto.getTeamDto();
        var teamEntity = teamMapper.toEntity(teamDto);

        updateTeamLeagueVenueAndSeasonYear(Integer.valueOf(seasonYear), leagueId, teamInfoDto, teamEntity);
        teamRepository.save(teamEntity);
    }

    public void updateTeamLeagueVenueAndSeasonYear(Integer season, Long id, TeamInfoDto teamInfo, @NotNull Team team) {
        var teamLeague = team.getLeague();
        var teamVenue = team.getVenue();
        if (teamLeague == null) {
            var league = leagueService.getByLeagueIdAndSeasonYear(id, season);
            if (league.isPresent()) {
                var leagueEntity = league.get();
                team.setLeague(leagueEntity);
                team.setSeason(season);
                if (teamVenue == null) {
                    team.setVenue(teamInfo.getVenue());
                }
            }
        }
    }

    public Optional<Team> findByTeamIdAndLeagueIdAndSeason(Long teamId, Long leagueId, int season) {
        return teamRepository.findByTeamIdAndLeagueIdAndSeason(teamId, leagueId, season);
    }
    public List<Team> findTeamsByLeagueIdAndSeason(Long leagueId, int season) {
        return teamRepository.findByLeagueIdAndSeason(leagueId, season);
    }
    public void save(Team teamEntity) {
        teamRepository.save(teamEntity);
    }

}
