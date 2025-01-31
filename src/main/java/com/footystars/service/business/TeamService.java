package com.footystars.service.business;

import com.footystars.model.api.TeamStatistics;
import com.footystars.model.api.TeamsInfo;
import com.footystars.model.entity.Team;
import com.footystars.model.dto.team.TeamDto;
import com.footystars.persistence.mapper.PlayerMapper;
import com.footystars.persistence.mapper.TeamMapper;
import com.footystars.persistence.mapper.TeamStatsMapper;
import com.footystars.persistence.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.footystars.utils.LogsNames.TEAMS_FETCH_ERROR;
import static com.footystars.utils.LogsNames.TEAMS_SEASON_ERROR;
import static com.footystars.utils.LogsNames.TEAM_NOT_FOUND_EXCEPTION;
import static com.footystars.utils.LogsNames.TEAM_STATS_ERROR;
import static com.footystars.utils.ParameterName.LEAGUE;
import static com.footystars.utils.ParameterName.SEASON;
import static com.footystars.utils.ParameterName.TEAM;

/**
 * Service responsible for managing teams and their related operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final TeamStatsMapper teamStatsMapper;
    private final LeagueService leagueService;
    private final FixtureService fixtureService;
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;


    /**
     * Retrieves a team by club ID, league ID, and season year.
     *
     * @param clubId   The club ID.
     * @param leagueId The league ID.
     * @param year     The season year.
     * @return An {@link Optional} containing the team if found.
     */
    public Optional<Team> getByClubIdLeagueIdAndYear(@NotNull Long clubId, @NotNull Long leagueId, @NotNull Integer year) {
        return teamRepository.findByInfoClubIdAndSeasonYearAndSeasonLeagueLeagueId(clubId, year, leagueId);
    }

    /**
     * Saves a team entity to the database.
     *
     * @param team The team entity to save.
     */
    @Transactional
    public void save(Team team) {
        teamRepository.save(team);
    }

    /**
     * Retrieves a list of club IDs for a given league and season.
     *
     * @param leagueId The league ID.
     * @param season   The season year.
     * @return A list of club IDs.
     */
    @Transactional(readOnly = true)
    public List<Long> getClubIdsByLeagueIdAndSeasonYear(@NotNull Long leagueId, @NotNull Integer season) {
        return teamRepository.findClubIdsByLeagueIdAndSeasonYear(leagueId, season);
    }

    /**
     * Retrieves a list of current season teams by club ID.
     *
     * @param clubId The club ID.
     * @return A list of {@link Team} objects representing the teams for the current season.
     */
    @Transactional(readOnly = true)
    public List<Team> getCurrentSeasonTeamsByClubId(Long clubId) {
        return teamRepository.findByIdAndLeagueSeasonCurrent(clubId, Boolean.TRUE);
    }

    /**
     * Retrieves a list of team DTOs representing seasons played by a given club.
     * Includes fixtures and players associated with the team for each season.
     *
     * @param clubId The club ID.
     * @return A list of {@link TeamDto} objects containing team information, fixtures, and players.
     */
    @Transactional(readOnly = true)
    public List<TeamDto> getTeamSeasonsByClubId(Long clubId) {
        try {
            var teams = teamRepository.findByIdAndLeagueSeasonCurrent(clubId, Boolean.TRUE);
            var teamsDto = teams.stream().map(teamMapper::toDto).toList();

            teamsDto.forEach(t -> {
                var matches = fixtureService.findCurrentSeasonFixturesByClubId(clubId);
                t.setMatches(matches);

                var players = playerService.findByLeagueIdAndClubId(clubId, t.getLeague().getId(), t.getLeague().getSeason());
                var playersDto = players.stream()
                        .map(playerMapper::toDto)
                        .toList();
                t.setPlayers(playersDto);
            });

            return teamsDto;
        } catch (Exception e) {
            log.error(TEAMS_SEASON_ERROR, clubId, e.getMessage());
            return List.of(); // Returning an empty list instead of null to prevent potential NullPointerException
        }
    }

    /**
     * Fetches and updates team information from API data.
     * If the team exists, it updates the information; otherwise, it creates a new team entity.
     *
     * @param leagueId   The league ID.
     * @param seasonYear The season year.
     * @param teamDto    The team data transfer object from API.
     */
    @Transactional
    public void fetchTeams(@NotNull Long leagueId, @NotNull Integer seasonYear, @NotNull TeamsInfo.TeamDto teamDto) {
        try {
            var clubId = teamDto.getInfo().getClubId();
            if (clubId != null) {
                var optionalTeam = getByClubIdLeagueIdAndYear(clubId, leagueId, seasonYear);
                if (optionalTeam.isPresent()) {
                    var team = optionalTeam.get();
                    teamMapper.partialUpdate(teamDto, team);
                    teamRepository.save(team);
                } else {
                    var optionalLeague = leagueService.findByLeagueIdAndSeason(leagueId, seasonYear);
                    if (optionalLeague.isPresent()) {
                        var league = optionalLeague.get();
                        var teamEntity = teamMapper.toEntity(teamDto);
                        teamEntity.setLeague(league);
                        teamEntity.getInfo().setClubId(teamDto.getInfo().getClubId());
                        teamRepository.save(teamEntity);
                    }
                }
            }
        } catch (Exception e) {
            log.error(TEAMS_FETCH_ERROR, leagueId, seasonYear, e.getMessage());
        }
    }

    /**
     * Fetches and updates team statistics from API data.
     * If the team exists in the system, it updates the statistics; otherwise, logs a warning.
     *
     * @param teamStatistic The team statistics API response.
     * @param params        A map containing league ID, season year, and team ID.
     */
    @Transactional
    public void fetchTeamStats(@NotNull TeamStatistics.TeamStatsApi teamStatistic, @NotNull Map<String, String> params) {
        try {
            var leagueId = Long.valueOf(params.get(LEAGUE));
            var season = Integer.parseInt(params.get(SEASON));
            var clubId = Long.valueOf(params.get(TEAM));
            var optionalTeam = getByClubIdLeagueIdAndYear(clubId, leagueId, season);
            if (optionalTeam.isPresent()) {
                var team = optionalTeam.get();
                var teamStats = teamStatsMapper.toEntity(teamStatistic);
                teamStats.setLastUpdated(ZonedDateTime.now());
                teamStats.setTeam(team);
                team.setStatistics(teamStats);
                teamRepository.save(team);
            } else {
                log.warn(TEAM_NOT_FOUND_EXCEPTION, clubId, leagueId, season);
            }
        } catch (Exception e) {
            log.error(TEAM_STATS_ERROR, params, e.getMessage());
        }
    }
}


