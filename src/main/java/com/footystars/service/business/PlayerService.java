package com.footystars.service.business;

import com.footystars.model.api.Fixtures;
import com.footystars.model.api.Players;
import com.footystars.model.dto.PlayerCareerDto;
import com.footystars.model.dto.PlayerLastMatchDto;
import com.footystars.model.dto.PlayerResponseDto;
import com.footystars.model.entity.FixturePlayer;
import com.footystars.model.entity.Player;
import com.footystars.persistence.mapper.PlayerMapper;
import com.footystars.persistence.repository.FixturePlayerRepository;
import com.footystars.persistence.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerMapper playerMapper;
    private final PlayerRepository playerRepository;
    private final FixturePlayerRepository fixturePlayerRepository;
    private final ZodiacService zodiacService;

    @Cacheable(value = "players", key = "#playerId + '-' + #leagueId + '-' + #season + '-' + #clubId")
    public Optional<Player> findByIdLeagueIdSeasonAndClubId(Long playerId, Long leagueId, Integer season, Long clubId) {
        return playerRepository.findByPlayerIdLeagueIdSeasonAndClubId(playerId, leagueId, season, clubId);
    }

    public List<Long> findPlayerIdsByLeagueId(@NotNull Long leagueId) {
        return playerRepository.findPlayerIdByLeagueId(leagueId);
    }

    @CachePut(value = "players", key = "#player.id + '-' + #player.statistics.league.leagueId + '-' + #player.statistics.league.season + '-' + #player.statistics.club.clubId")
    @Transactional
    public void updatePlayer(@NotNull Players.PlayerDto playerDto, @NotNull Player player) {
        playerMapper.partialUpdate(playerDto, player);
        playerRepository.save(player);
    }

    @CachePut(value = "players", key = "#playerEntity.info.playerId + '-' + #playerEntity.statistics.league.leagueId + '-' + #playerEntity.statistics.league.season + '-' + #playerEntity.statistics.club.clubId")
    @Transactional
    public void createPlayer(@NotNull Players.PlayerDto playerDto) {
        var playerEntity = playerMapper.toEntity(playerDto);
        var birthDate = playerEntity.getInfo().getBirth().getBirthDate();
        if (birthDate != null) {
            var zodiac = zodiacService.getZodiacSign(birthDate);
            playerEntity.getInfo().setZodiac(String.valueOf(zodiac));
        }
        playerRepository.save(playerEntity);
    }

    public List<Player> findByClubId(Long clubId, int season) {
        return playerRepository.findByClubIdAndSeason(clubId, season);
    }

    public List<Player> findByLeagueIdAndClubId(Long clubId, Long leagueId, int season) {
        return playerRepository.findByLeagueIdClubIdAndSeason(clubId, leagueId, season);
    }


    public PlayerResponseDto getPlayerDetails(Long playerId) {
        List<Player> players = playerRepository.findByInfoPlayerId(playerId);
        if (players.isEmpty()) {
            return null;
        }

        var player = players.get(0);
        List<FixturePlayer> lastMatchesByPlayerId = fixturePlayerRepository.findLastMatchesByPlayerId(playerId);

        var lastMatches = lastMatchesByPlayerId.stream()
                .map(fixturePlayer -> PlayerLastMatchDto.builder()
                        .matchDate(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getInfo().getDate()).orElse(null))
                        .leagueLogo(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getLeague().getLogo()).orElse(null))
                        .leagueName(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getLeague().getLeagueName()).orElse(null))
                        .homeTeamName(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getTeams().getHomeTeam().getHomeName()).orElse(null))
                        .homeTeamLogo(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getTeams().getHomeTeam().getHomeLogo()).orElse(null))
                        .awayTeamName(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getTeams().getAwayTeam().getAwayName()).orElse(null))
                        .awayTeamLogo(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getTeams().getAwayTeam().getAwayLogo()).orElse(null))
                        .minutes(fixturePlayer.getStats().stream()
                                .mapToInt(stats -> Optional.ofNullable(stats.getGames())
                                        .map(Fixtures.FixtureDto.FixturePlayer.FixturePlayerStats.Games::getMinutes)
                                        .orElse(0))
                                .sum())
                        .goals(fixturePlayer.getStats().stream()
                                .mapToInt(stats -> Optional.ofNullable(stats.getGoals())
                                        .map(Fixtures.FixtureDto.FixturePlayer.FixturePlayerStats.Goals::getGoalsTotal)
                                        .orElse(0))
                                .sum())
                        .assists(fixturePlayer.getStats().stream()
                                .mapToInt(stats -> Optional.ofNullable(stats.getGoals())
                                        .map(Fixtures.FixtureDto.FixturePlayer.FixturePlayerStats.Goals::getAssists)
                                        .orElse(0))
                                .sum())
                        .yellowCards(fixturePlayer.getStats().stream()
                                .mapToInt(stats -> Optional.ofNullable(stats.getCards())
                                        .map(Fixtures.FixtureDto.FixturePlayer.FixturePlayerStats.Cards::getYellow)
                                        .orElse(0))
                                .sum())
                        .redCards(fixturePlayer.getStats().stream()
                                .mapToInt(stats -> Optional.ofNullable(stats.getCards())
                                        .map(Fixtures.FixtureDto.FixturePlayer.FixturePlayerStats.Cards::getRed)
                                        .orElse(0))
                                .sum())
                        .rating(fixturePlayer.getStats().stream()
                                .map(stats -> Optional.ofNullable(stats.getGames())
                                        .map(Fixtures.FixtureDto.FixturePlayer.FixturePlayerStats.Games::getRating)
                                        .orElse("N/A"))
                                .filter(rating -> !rating.equals("N/A"))
                                .findFirst().orElse("N/A"))
                        .build())
                .toList();

        List<PlayerCareerDto> career = playerRepository.findByInfoPlayerId(playerId).stream()
                .map(playerMapper::toPlayerCareerDto)
                .toList();

        return PlayerResponseDto.builder()
                .info(player.getInfo())
                .lastMatches(lastMatches)
                .carrer(career)
                .build();
    }
}


