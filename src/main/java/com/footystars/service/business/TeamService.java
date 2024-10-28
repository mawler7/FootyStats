package com.footystars.service.business;

import com.footystars.model.api.TeamStatistics;
import com.footystars.model.api.TeamsInfo;
import com.footystars.model.entity.Team;
import com.footystars.model.entity.TeamDto;
import com.footystars.persistence.mapper.FixtureMapper;
import com.footystars.persistence.mapper.PlayerMapper;
import com.footystars.persistence.mapper.TeamMapper;
import com.footystars.persistence.mapper.TeamStatsMapper;
import com.footystars.persistence.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.footystars.utils.LogsNames.TEAM_NOT_FOUND_EXCEPTION;
import static com.footystars.utils.ParameterName.LEAGUE;
import static com.footystars.utils.ParameterName.SEASON;
import static com.footystars.utils.ParameterName.TEAM;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final TeamStatsMapper teamStatsMapper;
    private final LeagueService leagueService;
    private final FixtureService fixtureService;
    private final FixtureMapper fixtureMapper;
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    private final Logger logger = LoggerFactory.getLogger(TeamService.class);

    public Optional<Team> getByClubIdLeagueIdAndYear(@NotNull Long clubId, @NotNull Long leagueId, @NotNull Integer year) {
        return teamRepository.findByInfoClubIdAndSeasonYearAndSeasonLeagueLeagueId(clubId, year, leagueId);
    }

    @Transactional
    public void save(Team team) {
        teamRepository.save(team);
    }

    @Transactional(readOnly = true)
    public List<Long> getClubIdsByLeagueIdAndSeasonYear(@NotNull Long leagueId, @NotNull Integer season) {
        return teamRepository.findClubIdsByLeagueIdAndSeasonYear(leagueId, season);
    }

    @Transactional(readOnly = true)
    public List<Team> getCurrentSeasonTeamsByClubId(Long clubId) {
        return teamRepository.findByIdAndLeagueSeasonCurrent(clubId, Boolean.TRUE);
    }

    @Transactional(readOnly = true)
    public List<TeamDto> getTeamSeasonsByClubId(Long clubId) {
        var teams = teamRepository.findByIdAndLeagueSeasonCurrent(clubId, Boolean.TRUE);
        var teamsDto = teams.stream().map(teamMapper::toDto).toList();
            teamsDto.forEach(t -> {
                var matches = fixtureService.findCurrentSeasonFixturesByClubId(clubId);

                t.setMatches(matches);
                    var players = playerService.findByLeagueIdAndClubId(clubId, t.getLeague().getId(),  t.getLeague().getSeason());
                    var playersDto = players.stream()
                            .map(playerMapper::toDto)
                            .toList();
                    t.setPlayers(playersDto);
                });

        return teamsDto;

    }

    @Transactional
    public void fetchTeams(@NotNull Long leagueId, @NotNull Integer seasonYear, @NotNull TeamsInfo.TeamDto teamDto) {
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
    }

    @Transactional
    public void fetchTeamStats(@NotNull TeamStatistics.TeamStatsApi teamStatistic, @NotNull Map<String, String> params) {
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
            logger.warn(TEAM_NOT_FOUND_EXCEPTION, clubId, leagueId, season);
        }
    }


}