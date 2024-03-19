package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.fixtures.fixture.LeagueFixture;
import com.footystars.foot8.persistence.entities.fixtures.fixture.FixtureDto;
import com.footystars.foot8.persistence.entities.fixtures.fixture.FixtureMapper;
import com.footystars.foot8.persistence.repository.FixtureRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FixtureService {

    private final FixtureMapper fixtureMapper;
    private final FixtureRepository fixtureRepository;
    private final LeagueSeasonService leagueSeasonService;
    private final VenueService venueService;
    private final TeamSeasonService teamSeasonService;

    private static final Logger logger = LoggerFactory.getLogger(FixtureService.class);

    public void fetchFixture(@NotNull LeagueFixture leagueFixture) {

        var id = leagueFixture.getFixtureInfo().getFixtureId();
        if (id != null) {
            var optionalFixture = fixtureRepository.findById(id);
            if (optionalFixture.isPresent()) {
                var fixture = optionalFixture.get();
                var fixtureDto  = fixtureMapper.toDto(leagueFixture);
                fixtureMapper.partialUpdate(fixtureDto, fixture);
                logger.info("Updated fixture");
            } else {
                createFromDto(leagueFixture);
            }
            logger.info("Updated fixture with id: {id}");
        }


    }

    public void createFromDto(@NotNull LeagueFixture leagueFixture) {
        var season = leagueFixture.getLeague().getSeason();
        var leagueId = leagueFixture.getLeague().getLeagueId();
        var venueId = leagueFixture.getFixtureInfo().getVenue().getId();
        var homeTeamId = leagueFixture.getTeams().getHomeTeam().getTeamId();
        var awayTeamId = leagueFixture.getTeams().getAwayTeam().getTeamId();
        var optionalHomeTeam = teamSeasonService.findByTeamIdLeagueIdAndSeasonYear(homeTeamId,leagueId, season);
        var optionalAwayTeam = teamSeasonService.findByTeamIdLeagueIdAndSeasonYear(awayTeamId,leagueId, season);
        if(optionalHomeTeam.isPresent() && optionalAwayTeam.isPresent()) {
            var homeTeam = optionalHomeTeam.get();
            var awayTeam = optionalAwayTeam.get();

            var fixtureDto = fixtureMapper.toDto(leagueFixture);
            var fixture = fixtureMapper.toEntity(fixtureDto);
            fixture.setHomeTeam(homeTeam);
            fixture.setAwayTeam(awayTeam);

            if(venueId != null) {
                var optionalVenue = venueService.findById(venueId);
                if(optionalVenue.isPresent()) {

                    var venue = optionalVenue.get();
                    var seasonYear = leagueFixture.getLeague().getSeason();
                    var optionalLeagueSeason = leagueSeasonService.getByLeagueIdAndSeason(leagueId, seasonYear);
                    if (optionalLeagueSeason.isPresent()) {

                        var leagueSeason = optionalLeagueSeason.get();
                        fixture.setVenue(venue);
                        var savedFixture = fixtureRepository.save(fixture);
                        var fixtures = leagueSeason.getFixtures();
                        fixtures.add(savedFixture);
                        leagueSeasonService.save(leagueSeason);
                    }

                }
            }
        }


    }

}
