package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entities.countries.CountryMapper;
import com.footystars.foot8.persistence.entities.leagues.league.League;
import com.footystars.foot8.persistence.entities.leagues.league.LeagueDto;
import com.footystars.foot8.persistence.entities.leagues.league.LeagueMapper;
import com.footystars.foot8.persistence.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final LeagueSeasonService leagueSeasonService;
    private final LeagueMapper leagueMapper;
    private final CountryService countryService;
    private final CountryMapper countryMapper;

    @Transactional
    @Async
    public void fetchResponse(@NotNull LeagueDto leagueDto) {
        var optionalLeague = leagueRepository.findById(leagueDto.getLeagueInfo().getId());
        var seasons = leagueDto.getSeasons();
        var countryName = leagueDto.getCountry().getName();
        var country = countryService.findByName(countryName);
        if(country.isEmpty()) {
            countryService.save(leagueDto.getCountry());
        }

        if (optionalLeague.isPresent()) {
            var leagueEntity = optionalLeague.get();
            leagueSeasonService.updateSeasons(leagueEntity,seasons);
        } else {
            var newLeague = leagueMapper.toEntity(leagueDto);
            newLeague.setSeasons(new HashSet<>());
            var league = leagueRepository.saveAndFlush(newLeague);
            leagueSeasonService.updateSeasons(league, seasons);
        }
    }


    public Optional<League> findById(Long id) {
        return leagueRepository.findById(id);
    }
}
