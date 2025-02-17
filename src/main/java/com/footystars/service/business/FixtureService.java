package com.footystars.service.business;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.footystars.model.api.Fixtures;
import com.footystars.model.api.TeamStatistics;
import com.footystars.model.dto.bet.BetDto;
import com.footystars.model.dto.fixture.ClubMatchDto;
import com.footystars.model.dto.fixture.DBViewMatchDto;
import com.footystars.model.dto.fixture.H2HDto;
import com.footystars.model.dto.fixture.LeaguePredictionsDto;
import com.footystars.model.dto.fixture.MatchDetailsDto;
import com.footystars.model.dto.fixture.MatchDto;
import com.footystars.model.dto.fixture.MatchInfo;
import com.footystars.model.dto.fixture.MatchInfoDetails;
import com.footystars.model.dto.fixture.TeamPredictionStats;
import com.footystars.model.dto.team.TeamComeBacksDto;
import com.footystars.model.dto.team.TeamCorrectScoresDto;
import com.footystars.model.dto.team.TeamFormDto;
import com.footystars.model.dto.team.TeamHTDrawsDto;
import com.footystars.model.entity.Fixture;
import com.footystars.model.entity.FixturePlayer;
import com.footystars.model.dto.player.PlayerDto;
import com.footystars.model.dto.team.TeamDetailsDto;
import com.footystars.model.entity.Player;
import com.footystars.model.entity.Team;
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
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.StopWatch;
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
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.data.domain.PageImpl;
import org.springframework.beans.factory.annotation.Value;

import static com.footystars.utils.TopLeagues.getTopLeaguesIds;

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

    @Value("${PREDICTION_FILE_PATH:/app/data/predictions/}")
    private String predictionFilePath;

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





    public static ZonedDateTime getStartOfDay(String dateStr, String zoneId) {
        LocalDate localDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        return localDate.atStartOfDay(ZoneId.of(zoneId));
    }

    public static ZonedDateTime getEndOfDay(String dateStr, String zoneId) {
        LocalDate localDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        ZonedDateTime startOfNextDay = localDate.plusDays(1).atStartOfDay(ZoneId.of(zoneId));
        return startOfNextDay.minusNanos(1);
    }

    public List<MatchDto> getMatchesByDate(String dateStr) {

        ZonedDateTime startDate = getStartOfDay(dateStr, ZoneOffset.UTC.toString());
        ZonedDateTime endDate = getEndOfDay(dateStr, ZoneOffset.UTC.toString());

        CompletableFuture<List<MatchDto>> matchesFuture = CompletableFuture.supplyAsync(() -> {
            List<MatchDto> matches = fixtureRepository.findMatchDtosByDateRange(startDate, endDate);
            return matches;
        });

        CompletableFuture<List<BetDto>> betsFuture = matchesFuture.thenApply(matchDtos -> {
            List<Long> fixtureIds = matchDtos.stream()
                    .map(MatchDto::getId)
                    .collect(Collectors.toList());
            List<BetDto> bets = betRepository.findAverageOddsByFixtures(fixtureIds);
            return bets;
        });

        // 3. Łączenie wyników meczów i kursów zakładów
        CompletableFuture<List<MatchDto>> combinedMatchesFuture = matchesFuture.thenCombine(betsFuture, (matchDtos, bets) -> {
            Map<Long, List<BetDto>> betsByFixture = bets.stream()
                    .collect(Collectors.groupingBy(BetDto::getFixtureId));
            matchDtos.forEach(match ->
                    match.setBets(betsByFixture.getOrDefault(match.getId(), Collections.emptyList()))
            );
            return matchDtos;
        });

        List<MatchDto> matches = combinedMatchesFuture.join();
        Set<Long> teamIds = matches.stream()
                .flatMap(match -> Stream.of(match.getHomeTeamId(), match.getAwayTeamId()))
                .collect(Collectors.toSet());


        Map<Long, CompletableFuture<List<ClubMatchDto>>> h2hFutures = teamIds.stream()
                .collect(Collectors.toMap(
                        teamId -> teamId,
                        teamId -> CompletableFuture.supplyAsync(() -> {
                            return fixtureRepository.findLastMatchesByTeamId(teamId, PageRequest.of(0, 5));
                        })
                ));

        CompletableFuture<Void> allH2hFutures = CompletableFuture.allOf(
                h2hFutures.values().toArray(new CompletableFuture[0])
        );
        allH2hFutures.join();

        Map<Long, List<ClubMatchDto>> h2hMap = h2hFutures.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().join()
                ));

        matches.parallelStream().forEach(match -> {
            match.setHomeForm(h2hMap.getOrDefault(match.getAwayTeamId(), Collections.emptyList()));
            match.setAwayForm(h2hMap.getOrDefault(match.getHomeTeamId(), Collections.emptyList()));
        });


        return matches;
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
    @Transactional(readOnly = true)
    public MatchDetailsDto getMatchDetailsDto(Long id) {
        // Pobieramy główne dane meczu asynchronicznie
        CompletableFuture<MatchDetailsDto> matchFuture = CompletableFuture.supplyAsync(() ->
                fixtureRepository.findById(id)
                        .map(fixtureMapper::toMatchDetailsDto)
                        .orElseThrow(() -> new EntityNotFoundException("Match not found with id: " + id))
        );

        // Równoległe pobieranie zakładów dla meczu
        CompletableFuture<List<BetDto>> betsFuture = matchFuture.thenApply(match ->
                betRepository.findAverageOddsByFixtureId(id)
        );

        // Równoległe pobieranie drużyn dla gospodarzy i gości
        CompletableFuture<List<Team>> homeTeamsFuture = matchFuture.thenApply(match ->
                teamRepository.findByInfoClubId(match.getHomeTeamId())
        );
        CompletableFuture<List<Team>> awayTeamsFuture = matchFuture.thenApply(match ->
                teamRepository.findByInfoClubId(match.getAwayTeamId())
        );

        // Równoległe pobieranie identyfikatorów graczy dla obu drużyn
        CompletableFuture<List<Long>> homePlayerIdsFuture = matchFuture.thenApply(match ->
                playerRepository.findByInfoClubId(match.getHomeTeamId())
        );
        CompletableFuture<List<Long>> awayPlayerIdsFuture = matchFuture.thenApply(match ->
                playerRepository.findByInfoClubId(match.getAwayTeamId())
        );

        // Łączymy identyfikatory graczy i dla każdego id wykonujemy zapytanie findByInfoPlayerId
        CompletableFuture<List<Player>> playersFuture = homePlayerIdsFuture.thenCombine(awayPlayerIdsFuture,
                (homeIds, awayIds) -> {
                    // Usuwamy duplikaty
                    Set<Long> allIds = Stream.concat(homeIds.stream(), awayIds.stream())
                            .collect(Collectors.toSet());
                    // Dla każdego identyfikatora tworzymy asynchroniczne zapytanie
                    List<CompletableFuture<List<Player>>> futures = allIds.stream()
                            .map(playerId -> CompletableFuture.supplyAsync(() ->
                                    playerRepository.findByInfoPlayerId(playerId)
                            ))
                            .collect(Collectors.toList());
                    // Czekamy, aż wszystkie zapytania się zakończą
                    CompletableFuture<Void> allDone =
                            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
                    // Łączymy wyniki i zwracamy listę graczy
                    return allDone.thenApply(v ->
                            futures.stream()
                                    .flatMap(f -> f.join().stream())
                                    .collect(Collectors.toList())
                    ).join();
                }
        );

        // Łączymy wszystkie wyniki, ustawiając dane w DTO
        return matchFuture.thenCombine(betsFuture, (matchDto, bets) -> {
                    matchDto.setBets(bets);
                    return matchDto;
                })
                .thenCombine(homeTeamsFuture, (matchDto, homeTeams) -> {
                    List<TeamDetailsDto> homeTeamDetails = teamMapper.toTeamDetailList(homeTeams);
                    matchDto.setTeams(new ArrayList<>(homeTeamDetails));
                    return matchDto;
                })
                .thenCombine(awayTeamsFuture, (matchDto, awayTeams) -> {
                    List<TeamDetailsDto> awayTeamDetails = teamMapper.toTeamDetailList(awayTeams);
                    // Dodajemy drużyny gości do już ustawionej listy drużyn
                    matchDto.getTeams().addAll(awayTeamDetails);
                    return matchDto;
                })
                .thenCombine(playersFuture, (matchDto, players) -> {
                    matchDto.setPlayers(players);
                    return matchDto;
                })
                .join();
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
        List<ClubMatchDto> headToHeadMatches = fixtureRepository.findHeadToHeadMatches(homeId,awayId);
        List<ClubMatchDto> lastAwayMatches = fixtureRepository.findLastMatchesByTeamId(homeId,PageRequest.of(0, 50));
        List<ClubMatchDto> lastHomeMatches = fixtureRepository.findLastMatchesByTeamId(awayId, PageRequest.of(0, 50));

        return H2HDto.builder()
                .headToHeadMatches(headToHeadMatches)
                .lastAwayMatches(lastAwayMatches)
                .lastHomeMatches(lastHomeMatches)
                .build();

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

        List<BetDto> averageOdds = betRepository.findAverageOddsByFixtureId(fixtureId);

        MatchDetailsDto matchDetailsDto = fixtureMapper.toMatchDetailsDto(fixture);

        matchDetailsDto.setBets(averageOdds);

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
                        ZonedDateTime.ofInstant((Instant) result[4], ZoneOffset.UTC)
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
            if (!data.containsKey("homeTeamName") || !data.containsKey("awayTeamName")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Missing required fields: homeTeamName or awayTeamName");
            }

            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String filePath = String.format("%s%s_%s_%s.json", predictionFilePath,
                    data.get("homeTeamName"), data.get("awayTeamName"), date);

            File file = new File(filePath);

            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to create directory for predictions");
            }

            objectMapper.writeValue(file, data);
            return ResponseEntity.ok("File saved successfully: " + file.getAbsolutePath());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving file: " + e.getMessage());
        }
    }

    public List<LeaguePredictionsDto> getPredictionStatsForLeagues() {
        List<LeaguePredictionsDto> predictions = new ArrayList<>();
        var ids = getTopLeaguesIds();
        ids.forEach(leagueId -> {
            LeaguePredictionsDto leaguePredictions = fixtureRepository.findLeaguePredictions(leagueId);
            predictions.add(leaguePredictions);
        });
    return predictions;
    }


    public List<ClubMatchDto> findClubMatchDtosByClubId(Long clubId) {
        return fixtureRepository.findClubMatchDtosByClubIdLeagueId(clubId);
    }

    public TeamPredictionStats getTeamPredictionStats(Long clubId) {
        return fixtureRepository.findTeamPredictions(clubId);
    }
}

