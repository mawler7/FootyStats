package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.leagues.league.LeagueApi;
import com.footystars.foot8.exception.LeagueSaveException;
import com.footystars.foot8.persistence.entity.competitions.Competition;
import com.footystars.foot8.persistence.entity.leagues.League;
import com.footystars.foot8.persistence.entity.leagues.LeagueMapper;
import com.footystars.foot8.persistence.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final CountryService countryService;
    private final CompetitionService competitionService;
    private final SeasonService seasonService;
    private final LeagueMapper leagueMapper;

    private final Logger logger = LoggerFactory.getLogger(LeagueService.class);

    @Transactional
    public void fetchLeague(@NotNull LeagueApi leagueApi) {
        var league = getOrCreateLeague(leagueApi);
        logger.info("{} fetched successfully", league.getName() );

        var seasons = leagueApi.getSeasons();
        seasons.forEach(s -> competitionService.getByLeagueAndSeasonYear(league.getId(), s.getYear())
                .ifPresentOrElse(
                        competition -> logger.info("Competition for season {} already exists", s.getYear()),
                        () -> {
                            var season = seasonService.saveSeason(s, leagueApi.getLeagueInfo().getLeagueId());
                            logger.info("Season {} fetched",season.getYear());
                            season.setLeague(league);
                            var competition = Competition.builder()
                                    .league(league)
                                    .season(season)
                                    .build();
                            competitionService.createCompetition(competition);
                        }
                ));
    }

    private League getOrCreateLeague(@NotNull LeagueApi leagueApi) {
        return leagueRepository.findById(leagueApi.getLeagueInfo().getLeagueId())
                .map(league -> {
                    var leagueDto = leagueMapper.apiToDto(leagueApi);
                    return leagueMapper.partialUpdate(leagueDto, league);
                })
                .orElseGet(() -> {
                    var leagueDto = leagueMapper.apiToDto(leagueApi);
                    var leagueEntity = leagueMapper.toEntity(leagueDto);
                    return saveLeague(leagueEntity);
                });
    }


    public Optional<League> findById(Long id) {
        return leagueRepository.findById(id);
    }

    @Transactional
    public League saveLeague(@NotNull League league) throws LeagueSaveException {
        try {
            var optionalCountry = countryService.findByName(league.getCountry().getName());
            var country = optionalCountry.orElseGet(() -> countryService.save(league.getCountry()));
            league.setCountry(country);
            return leagueRepository.save(league);
        } catch (Exception e) {
            var errorMessage = "Error occurred while saving league: " + league.getName();
            logger.error(errorMessage, e);
            throw new LeagueSaveException(errorMessage, e);
        }
    }
}
