package com.footystars.service.business;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footystars.model.api.Fixtures;
import com.footystars.model.dto.bet.BetDto;
import com.footystars.model.dto.fixture.DBViewMatchDto;
import com.footystars.model.dto.fixture.H2HDto;
import com.footystars.model.dto.fixture.MatchDetailsDto;
import com.footystars.model.dto.fixture.MatchDto;
import com.footystars.model.dto.fixture.MatchInfo;
import com.footystars.model.dto.team.TeamComeBacksDto;
import com.footystars.model.dto.team.TeamCorrectScoresDto;
import com.footystars.model.dto.team.TeamHTDrawsDto;
import com.footystars.model.entity.Fixture;
import com.footystars.model.entity.FixturePlayer;
import com.footystars.model.dto.player.PlayerDto;
import com.footystars.model.dto.team.TeamDetailsDto;
import com.footystars.persistence.mapper.FixtureMapper;
import com.footystars.persistence.mapper.FixturePlayerMapper;
import com.footystars.persistence.mapper.PlayerMapper;
import com.footystars.persistence.mapper.TeamMapper;
import com.footystars.persistence.repository.BetRepository;
import com.footystars.persistence.repository.FixturePlayerRepository;
import com.footystars.persistence.repository.FixtureRepository;
import com.footystars.persistence.repository.FixtureSpecification;
import com.footystars.persistence.repository.PlayerRepository;
import com.footystars.persistence.repository.TeamRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageImpl;

/**
 * Service class responsible for managing Fixtures.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class FixtureService {

    private final FixtureRepository fixtureRepository;
    private final BetRepository betRepository;
    private final FixtureMapper fixtureMapper;
    private final LeagueService leagueService;
    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;
    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;
    private final ObjectMapper objectMapper;
    private final FixturePlayerRepository fixturePlayerRepository;
    private final FixturePlayerMapper fixturePlayerMapper;

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates a new fixture in the database.
     *
     * @param fixtureDto The fixture data transfer object.
     */
    @Transactional
    public void createFixture(@NotNull Fixtures.FixtureDto fixtureDto) {
        var fixtureEntity = fixtureMapper.toEntity(fixtureDto);
        updateFixtureAssociations(fixtureDto, fixtureEntity);
        fixtureRepository.save(fixtureEntity);
    }

    /**
     * Updates an existing fixture with new data.
     *
     * @param fixtureDto The updated fixture data.
     * @param fixture    The fixture entity to be updated.
     */
    @Transactional
    public void updateFixture(@NotNull Fixtures.FixtureDto fixtureDto, @NotNull Fixture fixture) {
        fixtureMapper.partialUpdate(fixtureDto, fixture);
        clearExistingAssociations(fixture);
        updateFixtureAssociations(fixtureDto, fixture);
        fixtureRepository.save(fixture);
    }

    /**
     * Finds a fixture by its ID.
     *
     * @param id The fixture ID.
     * @return An optional containing the fixture if found.
     */
    @Transactional(readOnly = true)
    public Optional<Fixture> findById(Long id) {
        return fixtureRepository.findById(id);
    }

    /**
     * Retrieves the IDs of fixtures scheduled for today.
     *
     * @return A list of fixture IDs.
     */
    @Transactional(readOnly = true)
    public List<Long> findTodayFixturesId() {
        return fixtureRepository.findTodayFixturesId();
    }

    /**
     * Retrieves fixtures that need updates for today.
     *
     * @return A list of fixtures scheduled for today.
     */
    @Transactional(readOnly = true)
    public List<Fixture> findTodayFixturesToUpdate() {
        var fixturesId = fixtureRepository.findTodayFixturesIdToUpdate();
        var todayFixtures = new ArrayList<Fixture>();
        fixturesId.forEach(id -> fixtureRepository.findById(id).ifPresent(todayFixtures::add));
        return todayFixtures;
    }

    /**
     * Retrieves live fixtures that require updates.
     *
     * @return A list of live fixtures.
     */
    public List<Fixture> findLiveFixturesToUpdate() {
        var fixturesId = fixtureRepository.findTodayLiveFixturesIdToUpdate();
        var liveFixtures = new ArrayList<Fixture>();
        fixturesId.forEach(id -> fixtureRepository.findById(id).ifPresent(liveFixtures::add));
        return liveFixtures;
    }

    /**
     * Saves a fixture in the database.
     *
     * @param fixture The fixture entity to be saved.
     */
    @Transactional
    public void save(Fixture fixture) {
        fixtureRepository.save(fixture);
    }

    /**
     * Retrieves fixture IDs that have unchecked predictions.
     *
     * @return A list of fixture IDs with pending predictions.
     */
    public List<Long> findUncheckedPredictions() {
        return fixtureRepository.findUnchecked();
    }

    /**
     * Retrieves fixture IDs that require an update.
     *
     * @return A list of fixture IDs that need updates.
     */
    @Transactional(readOnly = true)
    public List<Long> findFixtureIdsToUpdate() {
        return fixtureRepository.findFixturesIdToUpdate();
    }

    /**
     * Retrieves all fixtures scheduled for a specific date.
     *
     * @param date The date in format YYYY-MM-DD.
     * @return A list of matches played or scheduled on the given date.
     */
    public List<MatchDto> getMatchesByDate(String date) {
        List<Fixture> fixtures = fixtureRepository.findByDate(date);
        return getMatchDtos(fixtures);
    }

    /**
     * Retrieves fixture IDs that have outdated status.
     *
     * @return A list of fixture IDs requiring status updates.
     */
    @Transactional
    public List<Long> findFixturesWithStatusNotToDate() {
        return fixtureRepository.findByStatusNotToDate();
    }

    /**
     * Finds fixtures for a given league and season.
     *
     * @param leagueId The league ID.
     * @param season   The season year.
     * @return A list of fixtures matching the criteria.
     */
    public List<Fixture> findByLeagueIdAndSeason(@NotNull Long leagueId, @NotNull Integer season) {
        return fixtureRepository.findByLeagueIdAndSeason(leagueId, season);
    }

    /**
     * Finds fixture IDs for a given league and season.
     *
     * @param leagueId The league ID.
     * @param season   The season year.
     * @return A list of fixture IDs matching the criteria.
     */
    public List<Long> findFixtureIdsByLeagueIdAndSeason(@NotNull Long leagueId, @NotNull Integer season) {
        return fixtureRepository.findIdsByLeagueIdAndSeason(leagueId, season);
    }

    /**
     * Clears existing associations (events, players, lineups, statistics) for a fixture.
     *
     * @param fixture The fixture entity whose associations should be cleared.
     */
     void clearExistingAssociations(@NotNull Fixture fixture) {
        fixture.getEvents().clear();
        fixture.getPlayers().clear();
        fixture.getLineups().clear();
        fixture.getStatistics().clear();
    }

    /**
     * Retrieves match details based on fixture ID.
     *
     * @param id The fixture ID.
     * @return A match details DTO containing fixture information, or null if not found.
     */
    public MatchDetailsDto getMatchDetailsDto(Long id) {
        return fixtureRepository.findById(id)
                .map(fixtureMapper::toMatchDetailsDto)
                .orElse(null);
    }

    /**
     * Updates associations for a fixture based on the provided fixture DTO.
     *
     * @param fixtureDto    The fixture DTO containing new data.
     * @param fixtureEntity The fixture entity to be updated.
     */
    private void updateFixtureAssociations(@NotNull Fixtures.FixtureDto fixtureDto, @NotNull Fixture fixtureEntity) {
        fixtureEntity.setLastUpdated(ZonedDateTime.now());

        if (fixtureEntity.getPrediction() != null && fixtureEntity.getPrediction().getId() != null) {
            fixtureEntity.setPrediction(entityManager.merge(fixtureEntity.getPrediction()));
        }

        if (fixtureDto.getEvents() != null && !fixtureDto.getEvents().isEmpty()) {
            var events = fixtureMapper.toEventEntityList(fixtureDto.getEvents());
            events.forEach(event -> event.setFixture(fixtureEntity));
            fixtureEntity.getEvents().addAll(events);
        }

        if (fixtureDto.getLineups() != null && !fixtureDto.getLineups().isEmpty()) {
            var lineups = fixtureMapper.toLineupEntityList(fixtureDto.getLineups());
            lineups.forEach(lineup -> lineup.setFixture(fixtureEntity));
            fixtureEntity.getLineups().addAll(lineups);
        }

        if (fixtureDto.getStatistics() != null && !fixtureDto.getStatistics().isEmpty()) {
            for (var statistic : fixtureDto.getStatistics()) {
                var team = statistic.getTeam();
                var statsValues = statistic.getTeamStats();
                var fixtureStats = fixtureMapper.toStatisticEntities(statsValues);
                fixtureStats.forEach(stat -> {
                    stat.setFixture(fixtureEntity);
                    stat.setTeam(team);
                });
                fixtureEntity.getStatistics().addAll(fixtureStats);
            }
        }

        if (fixtureDto.getTeamPlayers() != null && !fixtureDto.getTeamPlayers().isEmpty()) {
            fixtureDto.getTeamPlayers().forEach(t -> {
                var players = t.getPlayers();
                var playersEntities = fixtureMapper.toPlayerEntityList(players);
                playersEntities.forEach(player -> player.setFixture(fixtureEntity));
                fixtureEntity.getPlayers().addAll(playersEntities);
            });
        }
    }

    /**
     * Retrieves match information, including details, players, and teams, based on fixture ID.
     *
     * @param id The fixture ID.
     * @return A {@link MatchInfo} object containing match details.
     */
    public MatchInfo getMatchInfoByFixtureId(@NotNull Long id) {
        MatchDetailsDto matchDetailsDto = getMatchDetailsDto(id);
        List<PlayerDto> playersStats = getPlayersStats(matchDetailsDto);
        Long awayTeamId = Long.valueOf(matchDetailsDto.getAwayTeamId());
        Long homeTeamId = Long.valueOf(matchDetailsDto.getHomeTeamId());
        var home = teamRepository.findByInfoClubId(homeTeamId);
        var away = teamRepository.findByInfoClubId(awayTeamId);

        var homeTeamStats = home.stream().map(teamMapper::toDto1).toList();
        var awayTeamStats = away.stream().map(teamMapper::toDto1).toList();

        List<TeamDetailsDto> result = new ArrayList<>();
        result.addAll(homeTeamStats);
        result.addAll(awayTeamStats);

        return MatchInfo.builder().matchDetails(matchDetailsDto).players(playersStats).teams(result).build();
    }

    /**
     * Retrieves player statistics for a given match.
     *
     * @param matchDetailsDto The match details DTO.
     * @return A list of {@link PlayerDto} objects containing player statistics.
     */
    @NotNull
    private List<PlayerDto> getPlayersStats(@NotNull MatchDetailsDto matchDetailsDto) {
        var players = matchDetailsDto.getPlayers();
        List<PlayerDto> list = new ArrayList<>();

        if (players.isEmpty()) {
            var leagueId = matchDetailsDto.getLeagueId();
            var season = matchDetailsDto.getSeason();
            var homeId = Long.valueOf(matchDetailsDto.getHomeTeamId());
            var awayId = Long.valueOf(matchDetailsDto.getAwayTeamId());
            var homePlayers = playerRepository.findByLeagueIdClubIdAndSeason(homeId, leagueId, season);
            var awayPlayers = playerRepository.findByLeagueIdClubIdAndSeason(awayId, leagueId, season);
            var homePlayersDtoList = homePlayers.stream().map(playerMapper::toPlayerDto).toList();
            var awayPlayersDtoList = awayPlayers.stream().map(playerMapper::toPlayerDto).toList();

            awayPlayersDtoList.forEach(p -> {
                List<FixturePlayer> lastMatchesByPlayerId = fixturePlayerRepository.findLastMatchesByPlayerId(p.getInfo().getPlayerId());
                var awayPlayersDto = fixturePlayerMapper.toDtoList(lastMatchesByPlayerId);
                p.setFixtures(awayPlayersDto);
            });

            homePlayersDtoList.forEach(p -> {
                List<FixturePlayer> lastMatchesByPlayerId = fixturePlayerRepository.findLastMatchesByPlayerId(p.getInfo().getPlayerId());
                var homePlayersDto = fixturePlayerMapper.toDtoList(lastMatchesByPlayerId);
                p.setFixtures(homePlayersDto);
            });

            list.addAll(homePlayersDtoList);
            list.addAll(awayPlayersDtoList);
        } else {
            players.forEach(player -> {
                var playersEntity = playerRepository.findByInfoPlayerId(player.getPlayer().getPlayerId());
                List<PlayerDto> playerDtoList = playersEntity.stream().map(playerMapper::toPlayerDto).toList();
                list.addAll(playerDtoList);
            });
        }
        return list;
    }

    /**
     * Retrieves a list of matches for the current season in a given league that have not started yet.
     *
     * @param leagueId The ID of the league.
     * @return A list of {@link MatchDto} containing upcoming fixtures.
     */
    public List<MatchDto> findCurrentSeasonFixturesByLeagueIdNotStarted(Long leagueId) {
        var optionalInteger = leagueService.findCurrentSeasonByLeagueId(leagueId);
        if (optionalInteger.isPresent()) {
            var season = optionalInteger.get();
            var fixtures = fixtureRepository.findByLeagueIdAndSeasonNotStarted(leagueId, season);
            if (!fixtures.isEmpty()) {
                return fixtures.stream().map(fixtureMapper::toMatchDto).toList();
            }
        }
        return List.of();
    }

    /**
     * Retrieves a list of matches for the current season in a given league that have already ended.
     *
     * @param leagueId The ID of the league.
     * @return A list of {@link MatchDto} containing finished fixtures.
     */
    public List<MatchDto> findCurrentSeasonFixturesByLeagueIdEnded(Long leagueId) {
        var optionalInteger = leagueService.findCurrentSeasonByLeagueId(leagueId);
        if (optionalInteger.isPresent()) {
            var season = optionalInteger.get();
            var fixtures = fixtureRepository.findByLeagueIdAndSeasonEnded(leagueId, season);
            if (!fixtures.isEmpty()) {
                return fixtures.stream().map(fixtureMapper::toMatchDto).toList();
            }
        }
        return List.of();
    }

    /**
     * Retrieves head-to-head (H2H) matches between two teams, including their last matches.
     *
     * @param homeId The ID of the home team.
     * @param awayId The ID of the away team.
     * @return An {@link H2HDto} containing last matches and direct encounters between the teams.
     */
    public H2HDto getHeadToHeadMatches(Long homeId, Long awayId) {
        var lastHomeFixtures = fixtureRepository.findLastMatchesByTeamId(homeId, PageRequest.of(0, 50));
        var lastAwayFixtures = fixtureRepository.findLastMatchesByTeamId(awayId, PageRequest.of(0, 50));
        var headToHeadFixtures = fixtureRepository.findHeadToHeadMatches(homeId, awayId);

        var lastHomeMatches = fixtureMapper.toMatchDtoList(lastHomeFixtures);
        var lastAwayMatches = fixtureMapper.toMatchDtoList(lastAwayFixtures);
        var headToHeadMatches = fixtureMapper.toMatchDtoList(headToHeadFixtures);

        return new H2HDto(lastHomeMatches, lastAwayMatches, headToHeadMatches);
    }

    /**
     * Retrieves a list of fixtures for a given club in the current season.
     *
     * @param clubId The ID of the club.
     * @return A list of {@link MatchDto} containing the club's fixtures.
     */
    public List<MatchDto> findCurrentSeasonFixturesByClubId(Long clubId) {
        var fixtures = fixtureRepository.findByClubIdAndSeasonCurrent(clubId, Boolean.TRUE);
        return fixtures.stream().map(fixtureMapper::toMatchDto).toList();
    }

    /**
     * Retrieves a list of fixtures within a 7-day range before and after the current date.
     *
     * @return A list of {@link MatchDto} containing fixtures in the time range.
     */
    @Transactional(readOnly = true)
    public List<MatchDto> findPreviousAndNext7DaysFixtures() {
        var today = ZonedDateTime.now();
        var startDate = today.minusDays(7);
        var endDate = today.plusDays(7);
        return fixtureRepository.findFixturesFromPreviousAndNext7Days(startDate, endDate).stream()
                .map(fixtureMapper::toMatchDto).toList();
    }

    /**
     * Converts a list of fixtures into a list of {@link MatchDto} objects, including average betting odds.
     *
     * @param fixtures The list of fixtures.
     * @return A list of {@link MatchDto} with match details and average odds.
     */
    @NotNull
    private List<MatchDto> getMatchDtos(List<Fixture> fixtures) {
        List<Long> fixtureIds = fixtures.stream().map(Fixture::getId).toList();

        List<Object[]> averageOddsData = betRepository.findAverageOddsByFixtures(fixtureIds);

        Map<Long, MatchDto> matchDtoMap = fixtures.stream()
                .collect(Collectors.toMap(Fixture::getId, fixtureMapper::toMatchDto));

        averageOddsData.forEach(odd -> {
            Long fixtureId = (Long) odd[0];
            String value = (String) odd[1];
            Double avgOdd = (Double) odd[2];
            MatchDto matchDto = matchDtoMap.get(fixtureId);

            switch (value) {
                case "Home" -> matchDto.setAverageHomeOdd(avgOdd);
                case "Draw" -> matchDto.setAverageDrawOdd(avgOdd);
                case "Away" -> matchDto.setAverageAwayOdd(avgOdd);
                default -> throw new IllegalArgumentException("Unexpected odd type: " + value);
            }
        });

        return new ArrayList<>(matchDtoMap.values());
    }

    /**
     * Retrieves match details along with average betting odds.
     *
     * @param fixtureId The fixture ID.
     * @return A {@link MatchDetailsDto} containing match details and betting odds.
     */
    @Transactional(readOnly = true)
    public MatchDetailsDto getMatchDetailsWithAverageBets(Long fixtureId) {
        var fixtureOptional = fixtureRepository.findById(fixtureId);
        if (fixtureOptional.isEmpty()) {
            throw new NoSuchElementException("Fixture not found for ID: " + fixtureId);
        }

        Fixture fixture = fixtureOptional.get();

        List<Object[]> averageOdds = betRepository.findAverageOddsByFixtureId(fixtureId);

        MatchDetailsDto matchDetailsDto = fixtureMapper.toMatchDetailsDto(fixture);

        List<BetDto> bets = averageOdds.stream()
                .map(odd -> BetDto.builder()
                        .betName((String) odd[0])
                        .odd((Double) odd[1])
                        .value(String.valueOf(odd[2]))
                        .bookmakerName(String.valueOf(odd[2]))
                        .build())
                .toList();

        matchDetailsDto.setBets(bets);

        return matchDetailsDto;
    }

    /**
     * Retrieves teams with the highest number of comeback wins.
     *
     * @return A list of {@link TeamComeBacksDto} containing comeback statistics.
     */
    @Transactional(readOnly = true)
    public List<TeamComeBacksDto> getTeamsWithComebacks() {
        return fixtureRepository.findTeamsWithDrawsAndPercentageAndLastDrawDate().stream()
                .map(result -> new TeamComeBacksDto(
                        (String) result[0],
                        (String) result[1],
                        ((Number) result[2]).intValue(),
                        ((Number) result[3]).intValue(),
                        LocalDateTime.ofInstant((Instant) result[4], ZoneId.systemDefault())
                ))
                .toList();
    }

    /**
     * Retrieves teams with the most correct score predictions.
     *
     * @return A list of {@link TeamCorrectScoresDto} containing correct score statistics.
     */
    @Transactional(readOnly = true)
    public List<TeamCorrectScoresDto> getTeamsMostCorrectScores() {
        return fixtureRepository.findTeamsMostCorrectScores().stream()
                .map(result -> new TeamCorrectScoresDto(
                        (String) result[0],
                        (String) result[1],
                        (String) result[2],
                        ((Number) result[3]).intValue(),
                        ((Number) result[4]).intValue()
                ))
                .toList();
    }

    /**
     * Retrieves teams with the most halftime draws.
     *
     * @return A list of {@link TeamHTDrawsDto} containing halftime draw statistics.
     */
    @Transactional(readOnly = true)
    public List<TeamHTDrawsDto> getTeamsMostHTDraws() {
        return fixtureRepository.findTeamsMostHTDraws().stream()
                .map(result -> new TeamHTDrawsDto(
                        (String) result[0],
                        (String) result[1],
                        ((Number) result[2]).intValue(),
                        ((Number) result[3]).intValue()
                ))
                .toList();
    }

    /**
     * Retrieves fixtures based on filtering criteria, including date range, league, and team names.
     *
     * @param startDate Start date filter.
     * @param endDate   End date filter.
     * @param leagueId  League ID filter.
     * @param homeName  Home team filter.
     * @param awayName  Away team filter.
     * @param pageable  Pagination details.
     * @return A paginated list of {@link DBViewMatchDto} containing filtered fixtures.
     */
    @Transactional(readOnly = true)
    public Page<DBViewMatchDto> getFixturesFiltered(String startDate, String endDate, Long leagueId, String homeName, String awayName, Pageable pageable) {
        ZonedDateTime startDateTime = (startDate != null) ? LocalDate.parse(startDate).atStartOfDay(ZoneId.systemDefault()) : null;
        ZonedDateTime endDateTime = (endDate != null) ? LocalDate.parse(endDate).atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()) : null;

        Specification<Fixture> spec = FixtureSpecification.filterByCriteria(startDateTime, endDateTime, leagueId, homeName, awayName);

        Page<Fixture> fixturesPage = fixtureRepository.findAll(spec, pageable);

        List<DBViewMatchDto> results = fixturesPage.stream()
                .map(fixtureMapper::toDBViewMatchDto)
                .sorted(Comparator.comparing(DBViewMatchDto::getDate))
                .toList();

        return new PageImpl<>(results, pageable, fixturesPage.getTotalElements());
    }

    /**
     * Saves a prediction to a JSON file.
     *
     * @param data a map containing prediction details, including "homeTeamName" and "awayTeamName".
     * @return a {@link ResponseEntity} indicating the success or failure of the operation.
     */
    public ResponseEntity<String> savePredictionToFile(Map<String, Object> data) {
        try {
            // Validate required fields
            if (!data.containsKey("homeTeamName") || !data.containsKey("awayTeamName")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Missing required fields: homeTeamName or awayTeamName");
            }

            // Generate filename with the current date
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String fileName = String.format("src/main/resources/predictions/%s_%s_%s_.json",
                    data.get("homeTeamName"), data.get("awayTeamName"), date);
            File file = new File(System.getProperty("user.dir"), fileName);

            // Ensure the directory exists
            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to create directory for predictions");
            }

            // Write data to JSON file
            objectMapper.writeValue(file, data);
            return ResponseEntity.ok("File saved successfully: " + file.getAbsolutePath());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving file: " + e.getMessage());
        }
    }

}

