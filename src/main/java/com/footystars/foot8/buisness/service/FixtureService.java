package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.fixtures.fixture.LeagueFixture;
import com.footystars.foot8.buisness.model.entity.Competition;
import com.footystars.foot8.buisness.model.entity.Fixture;
import com.footystars.foot8.mapper.FixtureMapper;
import com.footystars.foot8.repository.FixtureRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FixtureService {

    private final FixtureMapper fixtureMapper;
    private final FixtureRepository fixtureRepository;
    private final CompetitionService competitionService;
    private final VenueService venueService;
    private final ClubService clubService;
    private final TeamService teamService;

    private final Logger logger = LoggerFactory.getLogger(FixtureService.class);

    public void fetchFixture(@NotNull LeagueFixture leagueFixture) {
        var fixtureId = leagueFixture.getFixture().getFixtureId();
        if (fixtureId != null) {
            var optionalFixture = fixtureRepository.findById(fixtureId);
            if (optionalFixture.isPresent()) {
                var fixture = optionalFixture.get();
                var fixtureDto = fixtureMapper.toDto(leagueFixture);
                fixtureMapper.partialUpdate(fixtureDto, fixture);
                logger.info("Updated fixture");
            } else {
                createFromDto(leagueFixture);
                logger.info("Created fixture with fixtureId: {}", fixtureId);
            }
        }
    }

    public void createFromDto(@NotNull LeagueFixture leagueFixture) {
        var leagueId = leagueFixture.getLeague().getLeagueId();
        var venueId = leagueFixture.getFixture().getVenue().getId();
        var season = leagueFixture.getLeague().getSeason();

        var homeTeamId = leagueFixture.getTeams().getHomeTeam().getHomeTeamId();
        var awayTeamId = leagueFixture.getTeams().getAwayTeam().getAwayTeamId();
        var optionalHomeTeam = teamService.getByClubIdLeagueIdAndLeagueSeason(homeTeamId, leagueId,season);
        var optionalAwayTeam = teamService.getByClubIdLeagueIdAndLeagueSeason(awayTeamId, leagueId,season);

        if (optionalHomeTeam.isPresent() && optionalAwayTeam.isPresent()) {
            var homeTeam = optionalHomeTeam.get();
            var awayTeam = optionalAwayTeam.get();

            var fixtureDto = fixtureMapper.toDto(leagueFixture);
            var fixture = fixtureMapper.toEntity(fixtureDto);
            fixture.setHomeTeam(homeTeam);
            fixture.setAwayTeam(awayTeam);

            if (venueId != null) {
                var optionalVenue = venueService.findById(venueId);
                optionalVenue.ifPresentOrElse(
                        venue -> {
                            var seasonYear = leagueFixture.getLeague().getSeason();
                            var optionalCompetition = competitionService.getByLeagueAndSeasonYear(leagueId, seasonYear);
                            optionalCompetition.ifPresentOrElse(
                                    competition -> {
                                        var competitions = new HashSet<Competition>();
                                        competitions.add(competition);
                                        fixture.setVenue(venue);
                                        fixture.setCompetitions(competitions);
                                        var savedFixture = fixtureRepository.save(fixture);
                                        var fixtures = competition.getFixtures();
                                        fixtures.add(savedFixture);
                                        competitionService.save(competition);
                                    },
                                    () -> logger.error("Could not find competition for league {} and year {}", leagueId, seasonYear)
                            );
                        },
                        () -> logger.error("Could not find venue with id {}", venueId)
                );
            }
        }
    }

    @NotNull
    public Optional<Fixture> findById(Long id) {
        return fixtureRepository.findById(id);
    }

    public void save(Fixture fixture) {
        fixtureRepository.save(fixture);
    }
}