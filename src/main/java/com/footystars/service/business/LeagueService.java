package com.footystars.service.business;


import com.footystars.model.api.Leagues;
import com.footystars.model.dto.fixture.MatchScoreDto;
import com.footystars.model.dto.league.LeagueDetailsDto;
import com.footystars.model.dto.league.LeagueDto;
import com.footystars.model.dto.fixture.LeagueMatchDto;
import com.footystars.model.entity.League;
import com.footystars.persistence.mapper.FixtureMapper;
import com.footystars.persistence.mapper.LeagueMapper;
import com.footystars.persistence.repository.FixtureRepository;
import com.footystars.persistence.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


/**
 * Service class responsible for managing leagues.
 */
@Service
@RequiredArgsConstructor
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final LeagueMapper leagueMapper;
    private final FixtureRepository fixtureRepository;

    /**
     * Fetches and stores league data from the API response.
     *
     * @param leagueDto The league data transfer object.
     */
    @Transactional
    public void fetchLeague(@NotNull Leagues.LeagueDto leagueDto) {
        var seasons = leagueDto.getSeasons();
        seasons.forEach(s -> {
            var optionalLeague = leagueRepository.findByLeagueIdAndSeason(leagueDto.getInfo().getLeagueId(), s.getYear());
            if (optionalLeague.isEmpty()) {
                var league = leagueMapper.toEntity(leagueDto);
                var flag = leagueDto.getCountry().getCountryFlag();
                league.getInfo().setFlag(flag);
                league.setSeason(s);
                league.setInfo(leagueDto.getInfo());
                leagueRepository.save(league);
            } else {
                var league = optionalLeague.get();
                leagueMapper.partialUpdate(leagueDto, league);
                var flag = leagueDto.getCountry().getCountryFlag();
                league.getInfo().setFlag(flag);
                leagueRepository.save(league);
            }
        });
    }

    /**
     * Finds leagues by league ID.
     *
     * @param leagueId The ID of the league.
     * @return A list of leagues matching the given ID.
     */
    @Transactional(readOnly = true)
    public List<League> findByLeagueId(Long leagueId) {
        return leagueRepository.findByLeagueId(leagueId);
    }

    /**
     * Finds the current season year of a league.
     *
     * @param leagueId The ID of the league.
     * @return An optional containing the current season year if available.
     */
    @Transactional(readOnly = true)
    public Optional<Integer> findCurrentSeasonByLeagueId(Long leagueId) {
        return leagueRepository.findCurrentSeasonByLeagueId(leagueId, Boolean.TRUE);
    }

    /**
     * Finds a league by its ID and season year.
     *
     * @param leagueId    The ID of the league.
     * @param seasonYear  The season year.
     * @return An optional containing the league if found.
     */
    public Optional<League> findByLeagueIdAndSeason(Long leagueId, Integer seasonYear) {
        return leagueRepository.findByLeagueIdAndSeason(leagueId, seasonYear);
    }

    /**
     * Saves a league entity.
     *
     * @param league The league entity to save.
     */
    @Transactional
    public void save(League league) {
        leagueRepository.save(league);
    }

    /**
     * Retrieves leagues for the current season.
     *
     * @return A list of {@link LeagueDto} containing current season leagues.
     */
    public List<LeagueDto> findCurrentSeasonLeagues() {
        var currentSeasonLeagues = leagueRepository.findCurrentSeasonLeagues(Boolean.TRUE);
        return currentSeasonLeagues.stream().map(leagueMapper::toDto).toList();
    }

    /**
     * Retrieves league details including fixtures for the given league ID.
     *
     * @param leagueId The ID of the league.
     * @return A {@link LeagueDetailsDto} containing league information and fixtures.
     */
    public LeagueDetailsDto findLeagueSeasonsByLeagueId(Long leagueId) {
        var optionalSeason = leagueRepository.findCurrentSeasonByLeagueId(leagueId, Boolean.TRUE);
        if (optionalSeason.isPresent()) {
            var season = optionalSeason.get();
            var optionalLeague = findByLeagueIdAndSeason(leagueId, season);
            if (optionalLeague.isPresent()) {
                var league = optionalLeague.get();
                var fixtures = fixtureRepository.findFixturesByLeagueAndSeason(leagueId, season).stream()
                        .map(result -> new LeagueMatchDto(
                                (Long) result[0], // Fixture ID
                                ((ZonedDateTime) result[1]).toString(), // Match Date
                                (Long) result[2], // Home Team ID
                                (String) result[3], // Home Team Name
                                (String) result[4], // Home Team Logo
                                (Long) result[5], // Away Team ID
                                (String) result[6], // Away Team Name
                                (String) result[7], // Away Team Logo
                                (String) result[8], // League Name
                                (String) result[9], // League Logo
                                (Long) result[10], // League ID
                                (Integer) result[11], // Season Year
                                (String) result[12], // League Round
                                (String) result[13], // Match Elapsed Time
                                (String) result[14], // Match Status
                                new MatchScoreDto(
                                        (Integer) result[15], // Home Goals
                                        (Integer) result[16], // Away Goals
                                        (Integer) result[17], // Halftime Home Goals
                                        (Integer) result[18], // Halftime Away Goals
                                        (Integer) result[19], // Fulltime Home Goals
                                        (Integer) result[20], // Fulltime Away Goals
                                        (Integer) result[21], // Extra Time Home Goals
                                        (Integer) result[22], // Extra Time Away Goals
                                        (Integer) result[23], // Penalty Home Goals
                                        (Integer) result[24]  // Penalty Away Goals
                                )
                        ))
                        .toList();

                var leagueDto = leagueMapper.toLeagueDetailsDto(league);
                leagueDto.setFixtures(fixtures);
                return leagueDto;
            }
        }
        return null;
    }

}