package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.teams.TeamApi;
import com.footystars.foot8.persistence.entity.club.Club;
import com.footystars.foot8.persistence.entity.club.ClubMapper;
import com.footystars.foot8.persistence.entity.countries.Country;
import com.footystars.foot8.persistence.entity.teams.team.Team;
import com.footystars.foot8.persistence.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.footystars.foot8.utils.ParameterNames.LEAGUE;
import static com.footystars.foot8.utils.ParameterNames.SEASON;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;
    private final VenueService venueService;
    private final CompetitionService competitionService;
    private final TeamService teamService;
    private final ClubMapper clubMapper;

    private final Logger logger = LoggerFactory.getLogger(ClubService.class);

    @Transactional
    public void fetchClub(@NotNull TeamApi teamApi, @NotNull Map<String, String> params) {
        var leagueId = Long.valueOf(params.get(LEAGUE));
        var season = Integer.parseInt(params.get(SEASON));

        var optionalCompetition = competitionService.getByLeagueAndSeasonYear(leagueId, season);
        if (optionalCompetition.isPresent()) {
            var competition = optionalCompetition.get();

            var clubId = teamApi.getClub().getClubId();
            if (clubId != null) {
                var optionalClub = findById(clubId);

                if (optionalClub.isEmpty()) {
                    var country = competition.getLeague().getCountry();
                    var club = createClub(teamApi, country);

                    club.setCountry(country);
                    var savedClub = clubRepository.save(club);

                    var team = Team.builder()
                            .club(savedClub)
                            .competitions(List.of(competition))
                            .build();
                    teamService.save(team);
                    logger.info("Saved team {}" + " with season {}", team.getClub().getName(), season);
                } else {
                    var savedClub = optionalClub.get();

                    if (competition.getTeams().stream().noneMatch(team -> team.getClub().getId().equals(clubId))) {
                        var team = Team.builder()
                                .club(savedClub)
                                .competitions(List.of(competition))
                                .build();
                        teamService.save(team);
                        logger.info("Saved team {}" + " with season {}", team.getClub().getName(), season);

                    }
                }
            }
        }
    }

    @NotNull
    private Club createClub(@NotNull TeamApi teamApi, @NotNull Country country) {
        var clubDto = clubMapper.apiToDto(teamApi.getClub());
        var club = clubMapper.toEntity(clubDto);
        var venue = venueService.fetchVenue(teamApi);
        club.setVenue(venue);
        club.setCountry(country);
        return clubRepository.save(club);
    }

    public Optional<Club> findById(Long id) {
        return clubRepository.findById(id);
    }

}