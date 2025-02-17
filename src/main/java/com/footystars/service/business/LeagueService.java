package com.footystars.service.business;


import com.footystars.model.api.Leagues;
import com.footystars.model.api.Standings;
import com.footystars.model.dto.fixture.MatchDto;
import com.footystars.model.dto.league.LeagueDetailsDto;
import com.footystars.model.dto.league.LeagueDto;
import com.footystars.model.dto.league.LeagueInfoDto;
import com.footystars.model.entity.League;
import com.footystars.persistence.mapper.LeagueMapper;
import com.footystars.persistence.repository.FixtureRepository;
import com.footystars.persistence.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


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
        return leagueRepository.findCurrentSeasonByLeagueId(leagueId);
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
    public List<LeagueInfoDto> findCurrentSeasonLeagues() {
        return leagueRepository.findCurrentSeasonLeaguesInfo();
    }

    /**
     * Retrieves league details including fixtures for the given league ID.
     *
     * @param leagueId The ID of the league.
     * @return A {@link LeagueDetailsDto} containing league information and fixtures.
     */
    public LeagueDetailsDto findCurrentLeagueSeasonByLeagueId(Long leagueId) {
        CompletableFuture<LeagueInfoDto> leagueInfoFuture = CompletableFuture.supplyAsync(() ->
                {
                    try {
                        return leagueRepository.findLeagueInfoByLeagueId(leagueId)
                                .orElseThrow(ChangeSetPersister.NotFoundException::new);
                    } catch (ChangeSetPersister.NotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        CompletableFuture<List<Standings.StandingApi.StandingLeague.Standing>> standingsFuture = CompletableFuture.supplyAsync(() ->
                leagueRepository.findLeagueStandingsByLeagueId(leagueId)
        );

        CompletableFuture<List<MatchDto>> fixturesFuture = CompletableFuture.supplyAsync(() ->
                fixtureRepository.findCurrentSeasonMatchesByLeagueId(leagueId)
        );

        CompletableFuture.allOf(leagueInfoFuture, standingsFuture, fixturesFuture).join();

        return LeagueDetailsDto.builder()
                .leagueInfo(leagueInfoFuture.join())
                .standings(standingsFuture.join())
                .fixtures(fixturesFuture.join())
                .build();
    }

    public List<LeagueInfoDto> getLeaguesByClubId(Long clubId) {
        var year = LocalDate.now().getYear();
      return   leagueRepository.findLeaguesByClubId(clubId,year );
    }


}