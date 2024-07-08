package com.footystars.foot8.business.service;

import com.footystars.foot8.business.model.entity.Season;
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

    @Transactional(readOnly = true)
    public Optional<Season> findByLeagueIdAndYear(Long leagueId, int year) {
        return seasonRepository.findByLeagueIdAndYear(leagueId, year);

    }

    @Transactional(readOnly = true)
    public List<Season> findByLeagueId(Long leagueId) {
        return seasonRepository.findAllByLeagueId(leagueId);
    }


    @Transactional(readOnly = true)
    public Optional<Season> findCurrentSeasonByLeagueId(Long leagueId) {
        return seasonRepository.findByLeagueIdAndCurrentTrue(leagueId);
    }

    public void save(Season season) {
        seasonRepository.save(season);
    }

    public boolean isCurrent(@NotNull  Long leagueId, @NotNull Integer year) {
        return seasonRepository.existsByLeagueIdAndYearAndCurrentTrue(leagueId, year);
    }

}
