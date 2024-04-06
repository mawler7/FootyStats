package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.players.player.PlayerStatistics;
import com.footystars.foot8.api.model.players.statistics.PlayerStatisticApi;
import com.footystars.foot8.buisness.model.entity.Competition;
import com.footystars.foot8.mapper.PlayerMapper;
import com.footystars.foot8.buisness.model.entity.Team;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class PlayerInfoService {

    private final PlayerMapper playerMapper;
    private final PlayerService playerService;
    private final PlayerStatsService playerStatsService;
    private final CompetitionService competitionService;
    private final TeamService teamService;
    private final ZodiacService zodiacService;


    private final Logger logger = LoggerFactory.getLogger(PlayerInfoService.class);

    @Transactional
    public void fetchPlayers(@NotNull PlayerStatistics playerStatistics) {
        var statistic = playerStatistics.getStatistics().get(0);
        var clubId = statistic.getClub().getClubId();
        var leagueId = statistic.getLeague().getLeagueId();
        var season = statistic.getLeague().getSeason();
        var playerId = playerStatistics.getPlayerInfo().getPlayerId();


        var optionalCompetition = competitionService.getByLeagueAndSeasonYear(leagueId, season);
        if (optionalCompetition.isPresent()) {
            var competition = optionalCompetition.get();
            var optionalPlayer = playerService.findById(playerId);
            if (optionalPlayer.isPresent()) {
                playerStatsService.updatePlayerStatistics(playerId, statistic, competition);
            } else {
                var optionalTeam = competition.getTeams().stream().filter(t -> t.getClub().getId().equals(clubId)).findFirst();
                if (optionalTeam.isPresent()) {
                    var team = optionalTeam.get();
                    saveNewPlayer(playerId, playerStatistics, competition, statistic, team);
                }
            }
        }
    }

    private void saveNewPlayer(@NotNull Long playerId, @NotNull PlayerStatistics playerStatistics,
                               @NotNull Competition competition, @NotNull PlayerStatisticApi statistic,
                               @NotNull Team team) {

        var optionalPlayer = playerService.findById(playerId);
        if (optionalPlayer.isEmpty()) {
            var competitions = new HashSet<Competition>();
            competitions.add(competition);
            var teams = new HashSet<Team>();
            teams.add(team);
            var playerDto = playerMapper.playerInfoToDto(playerStatistics.getPlayerInfo());
            var playerEntity = playerMapper.toEntity(playerDto);
            var birthDate = playerEntity.getBirth().getBirthDate();
            if(birthDate != null) {
            var zodiac = zodiacService.getZodiacSign(birthDate);
                playerEntity.setZodiac(String.valueOf(zodiac));
            }
            playerEntity.setCompetitions(competitions);
            playerEntity.setTeams(teams);
            var savedPlayer = playerService.save(playerEntity);
            logger.info("Saved player {}", savedPlayer.getName());
            playerStatsService.updatePlayerStatistics(playerId, statistic, competition);

            competition.getPlayers().add(savedPlayer);
            team.getPlayers().add(savedPlayer);
            teamService.save(team);
            competitionService.save(competition);
        }
    }


}


