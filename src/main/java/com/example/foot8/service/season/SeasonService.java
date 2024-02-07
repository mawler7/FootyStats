package com.example.foot8.service.season;

import com.example.foot8.api.league.model.Seasons;
import com.example.foot8.exception.CurrentSeasonNotFoundException;
import com.example.foot8.persistence.entities.leagues.LeagueEntity;
import com.example.foot8.persistence.entities.seasons.SeasonsEntity;
import com.example.foot8.persistence.entities.seasons.SeasonsMapper;
import com.example.foot8.persistence.repository.SeasonsEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeasonService {

    private final SeasonsEntityRepository seasonRepository;
    private final SeasonsMapper seasonsMapper;

    public void saveOrUpdateSeasons(List<Seasons> seasons, LeagueEntity leagueEntity) {
        seasons.forEach(s -> {
            if (seasonRepository.findByYearAndStartDateAndEndDateAndLeague(s.getYear(), s.getStartDate(), s.getEndDate(), leagueEntity).isEmpty()) {
                SeasonsEntity seasonsEntity = seasonsMapper.toEntity(s);
                seasonsEntity.setLeague(leagueEntity);
                seasonRepository.save(seasonsEntity);
            }
        });
    }

    public Integer getCurrentSeasonYearForLeague(Long leagueId) throws CurrentSeasonNotFoundException {
        Optional<SeasonsEntity> currentSeason = seasonRepository.findByLeagueIdAndCurrentTrue(leagueId);
        if (currentSeason.isEmpty()) {
            throw new CurrentSeasonNotFoundException("Current season not found for league id: " + leagueId + " ");
        } else {
            return currentSeason.get().getYear();
        }
    }



}