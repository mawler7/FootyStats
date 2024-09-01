package com.footystars.service.business;

import com.footystars.model.api.Fixtures;
import com.footystars.persistence.entity.Fixture;
import com.footystars.persistence.mapper.FixtureMapper;
import com.footystars.persistence.repository.FixtureRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FixtureService {

    private final FixtureRepository fixtureRepository;
    private final FixtureMapper fixtureMapper;

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
            var statistics = fixtureMapper.toStatistcsEntityList(fixtureDto.getStatistics());
            statistics.forEach(stat -> stat.setFixture(fixtureEntity));
            fixtureEntity.getStatistics().addAll(statistics);
        }

        if (fixtureDto.getTeamPlayers() != null && !fixtureDto.getTeamPlayers().isEmpty()) {
            var players = fixtureMapper.toPlayerEntityList(fixtureDto.getTeamPlayers());
            players.forEach(player -> player.setFixture(fixtureEntity));
            fixtureEntity.getPlayers().addAll(players);
        }
    }

}