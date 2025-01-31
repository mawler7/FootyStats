package com.footystars.service.business;

import com.footystars.model.api.Fixtures;
import com.footystars.model.api.Players;
import com.footystars.model.dto.player.*;
import com.footystars.model.entity.Fixture;
import com.footystars.model.entity.FixturePlayer;
import com.footystars.model.entity.Player;
import com.footystars.persistence.mapper.PlayerMapper;
import com.footystars.persistence.repository.FixturePlayerRepository;
import com.footystars.persistence.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class responsible for managing Player-related operations.
 */
@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerMapper playerMapper;
    private final PlayerRepository playerRepository;
    private final FixturePlayerRepository fixturePlayerRepository;
    private final ZodiacService zodiacService;

    /**
     * Finds a player by player ID, league ID, season, and club ID.
     * The result is cached for improved performance.
     *
     * @param playerId The player's unique ID.
     * @param leagueId The league's ID.
     * @param season   The season year.
     * @param clubId   The club's ID.
     * @return An {@link Optional} containing the player if found.
     */
    @Cacheable(value = "players", key = "#playerId + '-' + #leagueId + '-' + #season + '-' + #clubId")
    public Optional<Player> findByIdLeagueIdSeasonAndClubId(Long playerId, Long leagueId, Integer season, Long clubId) {
        return playerRepository.findByPlayerIdLeagueIdSeasonAndClubId(playerId, leagueId, season, clubId);
    }

    /**
     * Retrieves a list of player IDs that belong to a specific league.
     *
     * @param leagueId The league's ID.
     * @return A list of player IDs.
     */
    public List<Long> findPlayerIdsByLeagueId(@NotNull Long leagueId) {
        return playerRepository.findPlayerIdByLeagueId(leagueId);
    }

    /**
     * Updates an existing player with new data.
     * The player is also updated in the cache.
     *
     * @param playerDto The updated player data.
     * @param player    The existing player entity to update.
     */
    @CachePut(value = "players", key = "#player.id + '-' + #player.statistics.league.leagueId + '-' + #player.statistics.league.season + '-' + #player.statistics.club.clubId")
    @Transactional
    public void updatePlayer(@NotNull Players.PlayerDto playerDto, @NotNull Player player) {
        playerMapper.partialUpdate(playerDto, player);
        playerRepository.save(player);
    }

    /**
     * Creates a new player entry in the database.
     * The player's zodiac sign is calculated based on their birthdate.
     *
     * @param playerDto The player data transfer object.
     */
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

    /**
     * Retrieves a list of players who belong to a specific club and league during a given season.
     *
     * @param clubId   The club's ID.
     * @param leagueId The league's ID.
     * @param season   The season year.
     * @return A list of {@link Player} entities.
     */
    public List<Player> findByLeagueIdAndClubId(Long clubId, Long leagueId, int season) {
        return playerRepository.findByLeagueIdClubIdAndSeason(clubId, leagueId, season);
    }

    /**
     * Retrieves details of a specific player, including last matches and career information.
     * This method manually maps match details and player statistics.
     *
     * @param playerId The ID of the player.
     * @return A {@link PlayerResponseDto} containing player details.
     */
    public PlayerResponseDto getPlayerDetails(Long playerId) {
        List<Player> players = playerRepository.findByInfoPlayerId(playerId);
        if (players.isEmpty()) {
            return null;
        }

        var player = players.get(0);
        List<FixturePlayer> lastMatchesByPlayerId = fixturePlayerRepository.findLastMatchesByPlayerId(playerId);

        var lastMatches = lastMatchesByPlayerId.stream()
                .map(fixturePlayer -> PlayerLastMatchDto.builder()
                        .id(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(Fixture::getId).orElse(null))
                        .matchDate(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getInfo().getDate()).orElse(null))
                        .leagueLogo(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getLeague().getLogo()).orElse(null))
                        .leagueName(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getLeague().getLeagueName()).orElse(null))
                        .homeTeamName(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getTeams().getHomeTeam().getHomeName()).orElse(null))
                        .homeScore(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getGoals().getHome()).orElse(null))
                        .homeTeamLogo(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getTeams().getHomeTeam().getHomeLogo()).orElse(null))
                        .awayTeamName(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getTeams().getAwayTeam().getAwayName()).orElse(null))
                        .awayTeamLogo(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getTeams().getAwayTeam().getAwayLogo()).orElse(null))
                        .awayScore(Optional.ofNullable(fixturePlayer.getFixture())
                                .map(f -> f.getGoals().getAway()).orElse(null))
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
                .career(career)
                .build();
    }

    /**
     * Retrieves the top goalscorers in a specific league.
     *
     * @param leagueId The league ID.
     * @return A list of {@link PlayerTopScorerDto} containing top scorers.
     */
    public List<PlayerTopScorerDto> getTopScorers(int leagueId) {
        List<Object[]> results = playerRepository.findTopScorersByLeagueId(leagueId);

        return results.stream()
                .map(this::mapToPlayerTopScorerDto)
                .toList();
    }

    /**
     * Maps an object array result set to a {@link PlayerTopScorerDto}.
     *
     * @param row The result row from a database query.
     * @return A {@link PlayerTopScorerDto} containing player scoring statistics.
     */
    @NotNull
    @Contract("_ -> new")
    private PlayerTopScorerDto mapToPlayerTopScorerDto(@NotNull Object[] row) {
        return new PlayerTopScorerDto(
                getValueAsLong(row[0]),
                getValueAsString(row[1]),
                getValueAsString(row[2]),
                getValueAsString(row[3]),
                getValueAsString(row[4]),
                getValueAsString(row[5]),
                getValueAsString(row[6]),
                getValueAsString(row[7]),
                getValueAsInteger(row[8]),
                getValueAsInteger(row[9]),
                getValueAsInteger(row[10]),
                getValueAsInteger(row[11]),
                getValueAsInteger(row[12]),
                getValueAsInteger(row[13]),
                getValueAsInteger(row[14]),
                getValueAsInteger(row[15]),
                getValueAsInteger(row[16]),
                getValueAsInteger(row[17]),
                getValueAsInteger(row[18]),
                getValueAsInteger(row[19]),
                getValueAsInteger(row[20])
        );
    }

    /**
     * Retrieves a value as a {@link String} from an object.
     *
     * @param value The value to convert.
     * @return The converted string value or {@code null} if the input is null.
     */
    private String getValueAsString(Object value) {
        return value != null ? (String) value : null;
    }

    /**
     * Retrieves a value as a {@link Long} from an object.
     *
     * @param value The value to convert.
     * @return The converted long value or {@code null} if the input is null.
     */
    private Long getValueAsLong(Object value) {
        return value != null ? (Long) value : null;
    }

    /**
     * Retrieves a value as an {@link Integer} from an object.
     * If the value is null, it returns {@code 0}.
     *
     * @param value The value to convert.
     * @return The converted integer value or {@code 0} if the input is null.
     */
    @NotNull
    @Contract(value = "!null -> param1", pure = true)
    private Integer getValueAsInteger(Object value) {
        return value != null ? (Integer) value : 0;
    }
}
