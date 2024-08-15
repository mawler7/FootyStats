package com.footystars.foot8.business.service;

import com.footystars.foot8.api.model.fixtures.fixture.FixtureApi;
import com.footystars.foot8.business.model.dto.FixtureDetailsDto;
import com.footystars.foot8.business.model.dto.FixturePredictionDto;
import com.footystars.foot8.business.model.entity.Fixture;
import com.footystars.foot8.mapper.FixtureMapper;
import com.footystars.foot8.repository.FixtureRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.footystars.foot8.utils.LogsNames.AWAY_TEAM_NOT_FOUND;
import static com.footystars.foot8.utils.LogsNames.FIXTURE_CREATED;
import static com.footystars.foot8.utils.LogsNames.FIXTURE_UPDATED;
import static com.footystars.foot8.utils.LogsNames.HOME_OR_AWAY_TEAM_NOT_FOUND;
import static com.footystars.foot8.utils.LogsNames.HOME_TEAM_NOT_FOUND;
import static com.footystars.foot8.utils.LogsNames.MATCH_CANCELLED;
import static com.footystars.foot8.utils.LogsNames.MATCH_FINISHED;
import static com.footystars.foot8.utils.LogsNames.SEASON_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FixtureService {


    private final FixtureRepository fixtureRepository;
    private final FixtureMapper fixtureMapper;
    private final SeasonService seasonService;
    private final TeamService teamService;

    private final Logger logger = LoggerFactory.getLogger(FixtureService.class);


    @Transactional
    public void fetchFixture(@NotNull FixtureApi leagueFixture) {
        var fixtureId = leagueFixture.getFixture().getFixtureId();
        var optionalFixture = fixtureRepository.findById(fixtureId);

        optionalFixture.ifPresentOrElse(fixture ->
                updateFixture(leagueFixture, fixture), () ->
                createFixture(leagueFixture)
        );

    }

    @Transactional
    public void fetchFixtureDetails(@NotNull FixtureDetailsDto fixtureDetailsDto) {
        var fixtureId = fixtureDetailsDto.getId();
        var optionalFixture = fixtureRepository.findById(fixtureId);

        optionalFixture.ifPresentOrElse(fixture ->
                updateFixtureDetails(fixtureDetailsDto, fixture), () ->
                createFixtureFromDetails(fixtureDetailsDto)
        );

    }

    @Transactional
    public void createFixture(@NotNull FixtureApi leagueFixture) {
        var fixtureDto = fixtureMapper.toDto(leagueFixture);
        var fixtureEntity = fixtureMapper.toEntity(fixtureDto);
        var season = leagueFixture.getLeague().getSeason();
        var leagueId = leagueFixture.getLeague().getLeagueId();

        setTeamsAndSeason(fixtureEntity, leagueFixture, leagueId, season);

        logger.info(FIXTURE_CREATED, leagueId, season, fixtureEntity.getId());

    }

    public void createFixtureFromDetails(@NotNull FixtureDetailsDto fixtureDetailsDto) {
        var fixtureDto = fixtureMapper.detailsToDto(fixtureDetailsDto);
        var fixtureEntity = fixtureMapper.toEntity(fixtureDto);
        var season = fixtureDetailsDto.getSeason().getYear();
        var leagueId = fixtureDetailsDto.getLeague().getId();

        setTeamsAndSeasonFromDetails(fixtureEntity, fixtureDetailsDto, leagueId, season);

        logger.info(FIXTURE_CREATED, leagueId, season, fixtureEntity.getId());

    }

    public void updateFixture(@NotNull FixtureApi leagueFixture, @NotNull Fixture fixture) {
        if (isFinalStatus(fixture.getFullStatus())) {
            if(fixture.getHomeTeam() == null || fixture.getAwayTeam() == null) {
                setTeamsAndSeason(fixture, leagueFixture, leagueFixture.getLeague().getLeagueId(), fixture.getSeason().getYear());
            }
            var fixtureDto = fixtureMapper.toDto(leagueFixture);
            fixtureDto.setLastUpdated(ZonedDateTime.now());
            fixtureMapper.partialUpdate(fixtureDto, fixture);
            logger.info(FIXTURE_UPDATED, fixture.getId());
        }
        fixtureRepository.save(fixture);
    }

    public void updateFixtureDetails(@NotNull FixtureDetailsDto fixtureDetailsDto, @NotNull Fixture fixture) {
        if (isFinalStatus(fixture.getFullStatus())) {
            var fixtureDto = fixtureMapper.detailsToDto(fixtureDetailsDto);
            fixtureMapper.partialUpdate(fixtureDto, fixture);
            logger.info(FIXTURE_UPDATED, fixture.getId());
        }
        fixtureRepository.save(fixture);
    }

    @Transactional
    public void setTeamsAndSeason(@NotNull Fixture fixture, @NotNull FixtureApi leagueFixture, @NotNull Long leagueId, @NotNull Integer season) {

        var homeTeamId = leagueFixture.getTeams().getHomeTeam().getHomeTeamId();
        var awayTeamId = leagueFixture.getTeams().getAwayTeam().getAwayTeamId();

        if (homeTeamId != null && awayTeamId != null) {
            teamService.getByClubIdLeagueIdAndYear(homeTeamId, leagueId, season)
                    .ifPresentOrElse(fixture::setHomeTeam,
                            () -> logger.warn(HOME_TEAM_NOT_FOUND, homeTeamId));

            teamService.getByClubIdLeagueIdAndYear(awayTeamId, leagueId, season)
                    .ifPresentOrElse(fixture::setAwayTeam,
                            () -> logger.warn(AWAY_TEAM_NOT_FOUND, awayTeamId));

            seasonService.findByLeagueIdAndYear(leagueId, season)
                    .ifPresentOrElse(s -> {
                        fixture.setSeason(s);
                        var newFixture = fixtureRepository.save(fixture);
                        s.getFixtures().add(newFixture);
                        seasonService.save(s);
                    }, () -> logger.warn(SEASON_NOT_FOUND, leagueId, season));
        } else {
            logger.warn(HOME_OR_AWAY_TEAM_NOT_FOUND, leagueFixture);
        }
    }

    public void setTeamsAndSeasonFromDetails(@NotNull Fixture fixture, @NotNull FixtureDetailsDto fixtureDetailsDto, @NotNull Long leagueId, @NotNull Integer season) {

        var homeTeamId = fixtureDetailsDto.getHomeTeam().getClubId();
        var awayTeamId = fixtureDetailsDto.getAwayTeam().getClubId();

        if (homeTeamId != null && awayTeamId != null) {
            teamService.getByClubIdLeagueIdAndYear(homeTeamId, leagueId, season)
                    .ifPresentOrElse(fixture::setHomeTeam,
                            () -> logger.warn(HOME_TEAM_NOT_FOUND, homeTeamId));

            teamService.getByClubIdLeagueIdAndYear(awayTeamId, leagueId, season)
                    .ifPresentOrElse(fixture::setAwayTeam,
                            () -> logger.warn(AWAY_TEAM_NOT_FOUND, awayTeamId));

            seasonService.findByLeagueIdAndYear(leagueId, season)
                    .ifPresentOrElse(s -> {
                        fixture.setSeason(s);
                        var newFixture = fixtureRepository.save(fixture);
                        s.getFixtures().add(newFixture);
                        seasonService.save(s);
                    }, () -> logger.warn(SEASON_NOT_FOUND, leagueId, season));
        } else {
            logger.warn(HOME_OR_AWAY_TEAM_NOT_FOUND, fixtureDetailsDto);
        }
    }

    public boolean isFinalStatus(@NotNull String longStatus) {
        return !longStatus.equals(MATCH_FINISHED) && !longStatus.equals(MATCH_CANCELLED);
    }

    public Optional<Fixture> findById(Long id) {
        return fixtureRepository.findById(id);
    }

    public List<Fixture> findByLeagueId(Long id) {
        return fixtureRepository.findBySeasonLeagueId(id);
    }
    public List<Fixture> findCurrentSeasonFixturesByLeagueId(Long id) {
        return fixtureRepository.findBySeasonLeagueId(id);
    }


    public List<Long> findTodayFixturesId() {
        return fixtureRepository.findTodayFixturesId();
    }


    @Async
    public CompletableFuture<List<FixtureDetailsDto>> findTodayFixturesDetails() {
        var todayFixtures = fixtureRepository.findTodayFixturesWithDetails();
        List<FixtureDetailsDto> details = todayFixtures.stream()
                .map(fixtureMapper::toDetailsDto)
                .toList();
        return CompletableFuture.completedFuture(details);
    }


    public List<FixtureDetailsDto> findBySeasonsLeagueIdAndSeasonsYear(Long id, Integer year) {
        var fixtures = fixtureRepository.findBySeasonLeagueIdAndSeasonYear(id, year);

        return fixtures.stream()
                .map(fixtureMapper::toDetailsDto)
                .toList();
    }

    public List<Fixture> findByYearAndLeagueId(Long id, Integer year) {
        return fixtureRepository.findBySeasonLeagueIdAndSeasonYear(id, year);


    }

    public void save(Fixture fixture) {
        fixtureRepository.save(fixture);
    }

    public boolean existsById(Long playerId) {
        return fixtureRepository.existsById(playerId);
    }

    public FixtureDetailsDto getFixtureDetailsByFixtureId(Long fixtureId) {
        return fixtureMapper.toDetailsDto(fixtureRepository.findById(fixtureId).orElse(null));
    }

    public List<FixtureDetailsDto> findByDate(String date) {
        ZonedDateTime startDate = ZonedDateTime.parse(date);
        ZonedDateTime endDate = startDate.plusDays(1).minusSeconds(1);

        return fixtureRepository.findNotStartedByDate(startDate, endDate).stream()
                .map(fixtureMapper::toDetailsDto)
                .toList();
    }

    public List<FixturePredictionDto> findTodayFixturesPredictions() {
        var formattedDate = ZonedDateTime.now().toString();
        return findFixturesPredictionsByDate(formattedDate);
    }

    public List<FixturePredictionDto> findFixturesPredictionsByDate(String date) {
        var startDate = ZonedDateTime.parse(date);
        var endDate = startDate.plusDays(1).minusSeconds(1);

        return fixtureRepository.findNotStartedByDate(startDate, endDate).stream()
                .map(fixtureMapper::toPredictionDto)
                .toList();
    }

    public List<Long> findFixturesLessThen30minBeforeKickOff() {
        return fixtureRepository.findTodayMatchesLessThen10minToStart();
    }



}
