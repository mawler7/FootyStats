package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entities.teams.seasons.TeamSeason;
import com.footystars.foot8.persistence.entities.teams.team.Team;
import com.footystars.foot8.persistence.entities.teams.team.TeamDto;
import com.footystars.foot8.persistence.entities.teams.team.TeamMapper;
import com.footystars.foot8.persistence.entities.venues.Venue;
import com.footystars.foot8.persistence.entities.venues.VenueMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;

@Service
@RequiredArgsConstructor
public class TeamInfoService {

    private final TeamMapper teamMapper;
    private final TeamSeasonService teamSeasonsService;
    private final TeamService teamService;
    private final LeagueSeasonService leagueSeasonService;
    private final VenueService venueService;
    private final VenueMapper venueMapper;

    @Transactional
    @Async
    public void fetchResponse(@NotNull TeamDto teamDto, @NotNull Map<String, String> params) {
        var seasonYear = Integer.parseInt(params.get(SEASON));
        var leagueId = Long.valueOf(params.get(LEAGUE));

        var team = getOrCreateTeamFromTeamDto(teamDto);

        var leagueSeason = leagueSeasonService.getByLeagueIdAndSeason(leagueId, seasonYear);

        if (leagueSeason.isPresent()) {
            var leagueSeasonEntity = leagueSeason.get();
            var teamSeasons = leagueSeasonEntity.getTeamSeasons();

            if (teamSeasons.stream()
                    .noneMatch(s -> s.getTeam().getId().equals(team.getId()))) {
                var teamSeason = TeamSeason.builder()
                        .team(team)
                        .leagueSeason(leagueSeasonEntity)
                        .build();
                var savedTeamSeason = teamSeasonsService.save(teamSeason);
                teamSeasons.add(savedTeamSeason);
                teamSeasonsService.save(teamSeason);
            }
        }
    }

    private Team getOrCreateTeamFromTeamDto(@NotNull TeamDto teamDto) {
        var venue = getOrCreateVenueFromTeamDto(teamDto);
        var teamId = teamDto.getTeamInfo().getTeamId();
        var optionalTeam = teamService.findById(teamId);
        if (optionalTeam.isEmpty()) {

            var team = teamMapper.toEntity(teamDto);
            team.setVenue(venue);
            return teamService.save(team);
        } else {
            return optionalTeam.get();

        }
    }


    @Nullable
    private Venue getOrCreateVenueFromTeamDto(@NotNull TeamDto teamDto) {
        var venueId = teamDto.getVenue().getId();
        var venueOptional = venueService.findById(venueId);
        if (venueOptional.isEmpty()) {
            var venueDto = teamDto.getVenue();
            venueDto.setCountry(teamDto.getTeamInfo().getCountry());
            var venue = venueMapper.toEntity(venueDto);
            return venueService.saveVenue(venue);
        }
        return venueOptional.get();
    }
}