package com.footystars.foot8.buisness.service;

import com.footystars.foot8.api.model.leagues.league.season.SeasonApi;
import com.footystars.foot8.buisness.model.entity.Season;
import com.footystars.foot8.mapper.SeasonMapper;
import com.footystars.foot8.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeasonService {

    private final SeasonRepository seasonRepository;
    private final SeasonMapper seasonMapper;

    @Transactional
    public Season saveSeason(@NotNull SeasonApi seasonApi, @NotNull Long leagueId) {
        var optionalSeason = findByLeagueIdAndYear(leagueId, seasonApi.getYear());
        if (optionalSeason.isEmpty()) {
            var seasonDto = seasonMapper.apiToDto(seasonApi);
            var season = seasonMapper.toEntity(seasonDto);
            return seasonRepository.save(season);
        } else {
            var season = optionalSeason.get();
            var seasonDto = seasonMapper.apiToDto(seasonApi);
            seasonMapper.partialUpdate(seasonDto, season);
            return seasonRepository.save(season);
        }
    }

    public Optional<Season> findByLeagueIdAndYear(Long leagueId, Integer year) {
        return seasonRepository.findByLeagueIdAndYear(leagueId, year);
    }

    public List<Integer> findByLeagueId(Long leagueId) {
        return seasonRepository.findByLeagueId(leagueId).stream()
               .map(Season::getYear)
               .toList();
    }

}
