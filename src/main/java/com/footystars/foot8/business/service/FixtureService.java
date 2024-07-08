package com.footystars.foot8.business.service;

import com.footystars.foot8.api.model.fixtures.fixture.LeagueFixture;
import com.footystars.foot8.business.model.dto.FixtureDetailsDto;
import com.footystars.foot8.business.model.dto.FixtureDto;
import com.footystars.foot8.business.model.entity.Fixture;
import com.footystars.foot8.mapper.FixtureMapper;
import com.footystars.foot8.repository.FixtureRepository;
import com.footystars.foot8.utils.SelectedLeagues;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    private final FixtureMapper fixtureMapper;
    private final FixtureRepository fixtureRepository;

    private final TeamService teamService;
    private final SeasonService seasonService;

    private final Logger logger = LoggerFactory.getLogger(FixtureService.class);

    @Transactional
    public void fetchFixture(@NotNull LeagueFixture leagueFixture) {
        var fixtureId = leagueFixture.getFixture().getFixtureId();
        var optionalFixture = fixtureRepository.findById(fixtureId);

        optionalFixture.ifPresentOrElse(fixture ->
                updateFixture(leagueFixture, fixture), () ->
                createFixture(leagueFixture)
        );

    }


    public void createFixture(@NotNull LeagueFixture leagueFixture) {
        var fixtureDto = fixtureMapper.toDto(leagueFixture);
        var fixtureEntity = fixtureMapper.toEntity(fixtureDto);
        var season = leagueFixture.getLeague().getSeason();
        var leagueId = leagueFixture.getLeague().getLeagueId();

        setTeamsAndSeason(fixtureEntity, leagueFixture, leagueId, season);

        logger.info(FIXTURE_CREATED, leagueId, season, fixtureEntity.getId());

    }


    public void updateFixture(@NotNull LeagueFixture leagueFixture, @NotNull Fixture fixture) {

        if (!isFinalStatus(fixture.getFullStatus())) {
            var fixtureDto = fixtureMapper.toDto(leagueFixture);
            fixtureMapper.partialUpdate(fixtureDto, fixture);
            logger.info(FIXTURE_UPDATED, fixture.getId());
        }

        fixtureRepository.save(fixture);
    }


    public void setTeamsAndSeason(@NotNull Fixture fixture, @NotNull LeagueFixture leagueFixture,
                                  @NotNull Long leagueId, @NotNull Integer season) {

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

    public boolean isFinalStatus(@NotNull String longStatus) {
        return longStatus.equals(MATCH_FINISHED) || longStatus.equals(MATCH_CANCELLED);
    }

    public Optional<Fixture> findById(Long id) {
        return fixtureRepository.findById(id);
    }
    public List<Fixture> findByLeagueId(Long id) {
        return fixtureRepository.findBySeasonLeagueId(id);
    }

    public List<Fixture> findTodayFixtures() {
        var date = LocalDateTime.now().toString();
        return fixtureRepository.findByDate(date);
    }

    public List<FixtureDetailsDto> findBySeasonsLeagueIdAndSeasonsYear(Long id, Integer year) {
        var fixtures = fixtureRepository.findBySeasonLeagueIdAndSeasonYear(id, year);
        return  fixtures.stream().map(fixtureMapper::toDetailsDto).toList();

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

}
