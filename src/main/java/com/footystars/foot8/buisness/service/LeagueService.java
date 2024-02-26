package com.footystars.foot8.buisness.service;


import com.footystars.foot8.api.model.league.LeagueResponse;
import com.footystars.foot8.api.model.league.SeasonDto;
import com.footystars.foot8.persistence.entities.countries.CountryDto;
import com.footystars.foot8.persistence.entities.leagues.League;
import com.footystars.foot8.persistence.entities.leagues.LeagueDomainMapper;
import com.footystars.foot8.persistence.entities.leagues.LeagueDto;
import com.footystars.foot8.persistence.entities.leagues.LeagueMapper;
import com.footystars.foot8.persistence.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final LeagueMapper leagueMapper;
    private final LeagueDomainMapper leagueDomainMapper;

    private static void updateSeasonAndCountry(@NotNull SeasonDto season, @NotNull CountryDto country, Integer seasonYear, @NotNull LeagueDto leagueDto) {
        leagueDto.setSeasonYear(seasonYear);
        leagueDto.setStartDate(season.getStartDate());
        leagueDto.setEndDate(season.getEndDate());
        leagueDto.setCountryName(country.getName());
        leagueDto.setCountryCode(country.getCode());
        leagueDto.setCountryFlag(country.getFlag());
    }

    public void createLeagueFromResponse(@NotNull LeagueResponse leagueResponse) {
        var season = leagueResponse.getSeasons().get(0);
        var country = leagueResponse.getCountry();
        var seasonYear = leagueResponse.getSeasons().get(0).getYear();
        var leagueDto = leagueDomainMapper.toDto(leagueResponse);
        updateSeasonAndCountry(season, country, seasonYear, leagueDto);
        var league = leagueMapper.toEntity(leagueDto);
        leagueRepository.save(league);
    }

    @Transactional
    public void updateFromResponse(@NotNull LeagueResponse leagueResponse) {
        CountryDto country = leagueResponse.getCountry();
        var leagueId = leagueResponse.getLeagueInfo().getLeagueId();
        var seasonYear = leagueResponse.getSeasons().get(0).getYear();

        var season = leagueResponse.getSeasons().get(0);

        if (leagueId != null && seasonYear != null) {
            Optional<League> league = getByLeagueIdAndSeasonYear(leagueId, seasonYear);
            if (league.isPresent()) {
                League leagueEntity = league.get();
                if (leagueEntity.getSeasonYear() == null || leagueEntity.getStartDate() == null || leagueEntity.getEndDate() == null) {
                    leagueEntity.setSeasonYear(seasonYear);
                    leagueEntity.setStartDate(season.getStartDate());
                    leagueEntity.setEndDate(season.getEndDate());
                }
                if (leagueEntity.getCountryName() == null && country != null) {
                    leagueEntity.setCountryName(country.getName());
                    leagueEntity.setCountryCode(country.getCode());
                    leagueEntity.setCountryFlag(country.getFlag());
                }
                LeagueDto leagueDto = leagueMapper.toDto(leagueEntity);
                leagueDomainMapper.partialUpdate(leagueResponse, leagueDto);
                League updatedEntity = leagueMapper.toEntity(leagueDto);
                save(updatedEntity);
            } else {

                createLeagueFromResponse(leagueResponse);
            }
        }
    }

    public Optional<League> getByLeagueIdAndSeasonYear(@NotNull Long leagueId, @NotNull Integer seasonYear) {
        return leagueRepository.findByLeagueIdAndSeasonYear(leagueId, seasonYear);
    }

    public void save(League leagueEntity) {
        leagueRepository.save(leagueEntity);
    }


}
