package com.footystars.service.business;

import com.footystars.model.api.Fixtures;
import com.footystars.model.dto.H2HDto;
import com.footystars.model.dto.MatchDetailsDto;
import com.footystars.model.dto.MatchDto;
import com.footystars.model.entity.Fixture;
import com.footystars.persistence.mapper.FixtureMapper;
import com.footystars.persistence.repository.FixtureRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FixtureService {

    private final FixtureRepository fixtureRepository;
    private final FixtureMapper fixtureMapper;
    private final LeagueService leagueService;

    @Transactional
    public void createFixture(@NotNull Fixtures.FixtureDto fixtureDto) {
        var fixtureEntity = fixtureMapper.toEntity(fixtureDto);
        updateFixtureAssociations(fixtureDto, fixtureEntity);
        fixtureRepository.save(fixtureEntity);
    }

    @Transactional
    public void updateFixture(@NotNull Fixtures.FixtureDto fixtureDto, @NotNull Fixture fixture) {
        fixtureMapper.partialUpdate(fixtureDto, fixture);
        clearExistingAssociations(fixture);
        updateFixtureAssociations(fixtureDto, fixture);
        fixtureRepository.save(fixture);
    }

    @Transactional(readOnly = true)
    public Optional<Fixture> findById(Long id) {
        return fixtureRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Long> findTodayFixturesId() {
        return fixtureRepository.findTodayFixturesId();
    }

    @Transactional(readOnly = true)
    public List<Fixture> findTodayFixturesToUpdate() {
        var fixturesId = fixtureRepository.findTodayFixturesIdToUpdate();
        var todayFixtures = new ArrayList<Fixture>();
        fixturesId.forEach(id -> fixtureRepository.findById(id).ifPresent(todayFixtures::add));
        return todayFixtures;
    }

    @Transactional(readOnly = true)
    public List<Fixture> findLiveFixturesToUpdate() {
        var fixturesId = fixtureRepository.findTodayLiveFixturesIdToUpdate();
        var todayFixtures = new ArrayList<Fixture>();
        fixturesId.forEach(id -> fixtureRepository.findById(id).ifPresent(todayFixtures::add));
        return todayFixtures;
    }

    @Transactional
    public void save(Fixture fixture) {
        fixtureRepository.save(fixture);
    }

    public List<Fixture> findByLeagueIdAndSeason(@NotNull Long leagueId, @NotNull Integer season) {
        return fixtureRepository.findByLeagueIdAndSeason(leagueId, season);
    }

    public List<Long> findFixtureIdsByLeagueIdAndSeason(@NotNull Long leagueId, @NotNull Integer season) {
        return fixtureRepository.findIdsByLeagueIdAndSeason(leagueId, season);
    }

    private void clearExistingAssociations(@NotNull Fixture fixture) {
        fixture.getEvents().clear();
        fixture.getPlayers().clear();
        fixture.getLineups().clear();
        fixture.getStatistics().clear();
    }

    private void updateFixtureAssociations(@NotNull Fixtures.FixtureDto fixtureDto, @NotNull Fixture fixtureEntity) {
        fixtureEntity.setLastUpdated(ZonedDateTime.now());

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

    public List<MatchDto> getMatchesByDate(String date) {
        var fixtures = fixtureRepository.findByDate(date);

        return fixtures.stream().map(fixtureMapper::toMatchDto).toList();
    }

    public MatchDetailsDto getFixtureDtoByFixtureId(Long id) {
        var optionalFixture = fixtureRepository.findById(id);
        if (optionalFixture.isPresent()) {
            var fixture = optionalFixture.get();
            return fixtureMapper.toMatchDetailsDto(fixture);

        }
        return null;

    }

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

    public List<Long> findUncheckedPredictions() {
        return fixtureRepository.findUnchecked();
    }

    public List<Fixture> findByLeagueId(Long leagueId) {
        return fixtureRepository.findByLeagueId(leagueId);
    }

    public H2HDto getHeadToHeadMatches(Long homeId, Long awayId) {
        var lastHomeFixtures = fixtureRepository.findLastMatchesByTeamId(homeId, PageRequest.of(0, 50));
        var lastAwayFixtures = fixtureRepository.findLastMatchesByTeamId(awayId, PageRequest.of(0, 50));
        var headToHeadFixtures = fixtureRepository.findHeadToHeadMatches(homeId, awayId);

        var lastHomeMatches = fixtureMapper.toMatchDtoList(lastHomeFixtures);
        var lastAwayMatches = fixtureMapper.toMatchDtoList(lastAwayFixtures);
        var headToHeadMatches = fixtureMapper.toMatchDtoList(headToHeadFixtures);

        return new H2HDto(lastHomeMatches, lastAwayMatches, headToHeadMatches);
    }

    @Transactional(readOnly = true)
    public List<Long> findFixtureIdsToUpdate() {
        return fixtureRepository.findFixturesIdToUpdate();
    }

    public List<MatchDto> findCurrentSeasonFixturesByClubId(Long clubId) {
        var fixtures = fixtureRepository.findByClubIdAndSeasonCurrent(clubId, Boolean.TRUE);
        return fixtures.stream().map(fixtureMapper::toMatchDto).toList();
    }

    @Transactional(readOnly = true)
    public List<Long> findCurrentSeasonFixtures() {
        return fixtureRepository.findCurrentSeasonFixtures(Boolean.TRUE);
    }

    public List<MatchDetailsDto> findPreviousAndNext7DaysFixtures() {
        var today = ZonedDateTime.now();
        var startDate = today.minusDays(7);
        var endDate = today.plusDays(7);
        return fixtureRepository.findFixturesFromPreviousAndNext7Days(startDate, endDate).stream()
                .map(fixtureMapper::toMatchDetailsDto).toList();

    }


}