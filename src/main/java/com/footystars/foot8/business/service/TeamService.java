package com.footystars.foot8.business.service;

import com.footystars.foot8.api.model.teams.TeamApi;
import com.footystars.foot8.business.model.entity.Team;
import com.footystars.foot8.mapper.TeamMapper;
import com.footystars.foot8.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.footystars.foot8.utils.LogsNames.FETCH_CLUBS_ERROR;
import static com.footystars.foot8.utils.LogsNames.NO_SEASON_ERROR;
import static com.footystars.foot8.utils.LogsNames.TEAM_SAVED;
import static com.footystars.foot8.utils.LogsNames.TEAM_UPDATED;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final SeasonService seasonService;

    private final Logger logger = LoggerFactory.getLogger(TeamService.class);

    public Optional<Team> getByClubIdLeagueIdAndYear(@NotNull Long clubId, @NotNull Long leagueId, @NotNull Integer year) {
        var optionalSeason = seasonService.findByLeagueIdAndYear(leagueId, year);
        if (optionalSeason.isPresent()) {
            var season = optionalSeason.get();
            return teamRepository.findByClubIdAndSeasonId(clubId, season.getId());
        }
        return Optional.empty();
    }

    public void save(Team team) {
        teamRepository.save(team);
    }

    public List<Team> getByLeagueIdAndSeason(@NotNull Long leagueId, @NotNull Integer season) {
        return teamRepository.findBySeasonLeagueIdAndSeasonYear(leagueId, season);
    }

    public List<Team> getCurrentSeasonTeamsByClubId(Long clubId) {
        return teamRepository.findByClubIdAndSeasonCurrent(clubId, Boolean.TRUE);
    }

    public List<Team> getTeamsBySeasonId(Long seasonId) {
        return teamRepository.findBySeasonId(seasonId);
    }

    @Transactional
    public void fetchClubs(@NotNull List<TeamApi> teamsApi, @NotNull Long leagueId, @NotNull Integer year) {
        try {
            teamsApi.parallelStream().forEach(t ->
                    fetchTeams(leagueId, year, t));
        } catch (Exception e) {
            logger.error(FETCH_CLUBS_ERROR, e);
        }
    }

    @Transactional
    public void fetchTeams(@NotNull Long leagueId, @NotNull Integer seasonYear, @NotNull TeamApi teamApi) {

        var clubId = teamApi.getTeamInfo().getClubId();

        if (clubId != null) {
            var optionalTeam = getByClubIdLeagueIdAndYear(clubId, leagueId, seasonYear);

            if (optionalTeam.isPresent()) {
                var team = optionalTeam.get();
                var teamDto = teamMapper.teamApiToTeamDto(teamApi);
                teamMapper.partialUpdate(teamDto, team);
                teamRepository.save(team);

                logger.info(TEAM_UPDATED, team.getName());
            } else {
                var optionalSeason = seasonService.findByLeagueIdAndYear(leagueId, seasonYear);

                if (optionalSeason.isPresent()) {
                    var season = optionalSeason.get();

                    // Ensure season is managed
                    season = seasonService.attachSeason(season);

                    var teamDto = teamMapper.teamApiToTeamDto(teamApi);
                    var teamEntity = teamMapper.toEntity(teamDto);
                    teamEntity.setSeason(season);

                    teamRepository.save(teamEntity);

                    logger.info(TEAM_SAVED, teamEntity.getName());
                } else {
                    logger.warn(NO_SEASON_ERROR, leagueId, seasonYear);
                }
            }
        }
    }
}