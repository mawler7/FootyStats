package com.footystars.foot8.business.service;

import com.footystars.foot8.api.model.players.player.PlayerApi;
import com.footystars.foot8.business.model.entity.Player;
import com.footystars.foot8.mapper.PlayerMapper;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.footystars.foot8.utils.LogsNames.PLAYER_ERROR;

@Service
@RequiredArgsConstructor
public class PlayerInfoService {

    private final PlayerMapper playerMapper;
    private final PlayerService playerService;
    private final TeamService teamService;
    private final ZodiacService zodiacService;
    private final PlayerStatsService playerStatsService;
    private final SeasonService seasonService;

    private final Logger logger = LoggerFactory.getLogger(PlayerInfoService.class);

    @Transactional
    public void createPlayer(@NotNull PlayerApi playerStatistics) {
        var playerId = playerStatistics.getPlayerInfo().getPlayerId();
        var optionalPlayer = playerService.findById(playerId);

        if (optionalPlayer.isEmpty()) {
            try {
                var playerDto = playerMapper.playerInfoToDto(playerStatistics.getPlayerInfo());
                var playerEntity = playerMapper.toEntity(playerDto);
                var birthDate = playerEntity.getBirth().getBirthDate();

                if (birthDate != null) {
                    var zodiac = zodiacService.getZodiacSign(birthDate);
                    playerEntity.setZodiac(String.valueOf(zodiac));
                }

                var clubId = playerStatistics.getStatistics().get(0).getClub().getClubId();
                var leagueId = playerStatistics.getStatistics().get(0).getLeague().getLeagueId();
                var year = playerStatistics.getStatistics().get(0).getLeague().getYear();

               var optionalSeason = seasonService.findByLeagueIdAndYear(leagueId, year);

                if(optionalSeason.isPresent()) {
                    var season = optionalSeason.get();
                    var teams = season.getTeams();

                    var teamOptional = teams.stream()
                            .filter(t -> t.getClubId().equals(clubId))
                            .findFirst();

                    if(teamOptional.isPresent()) {
                        var team = teamOptional.get();
                        var teamList = List.of(team);
                        playerEntity.setTeams(teamList);
                        Player savedPlayer = playerService.save(playerEntity);
                        Set<Player> players = team.getPlayers();
                        if(!players.contains(savedPlayer)) {
                            players.add(savedPlayer);
                            teamService.save(team);
                            playerStatsService.createPlayerStats(savedPlayer, playerStatistics);
                        }
                    }
                }
            } catch (DataIntegrityViolationException e) {
                logger.error(PLAYER_ERROR, e.getMessage());
            }
        }
    }


}
