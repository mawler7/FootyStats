package com.footystars.foot8.business.service;

import com.footystars.foot8.business.model.entity.Season;
import com.footystars.foot8.repository.SeasonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeasonService {

    private final SeasonRepository seasonRepository;

    @PersistenceContext
    private EntityManager entityManager;

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

    @Transactional
    public void save(Season season) {
        seasonRepository.save(season);
    }

    public boolean isCurrent(@NotNull Long leagueId, @NotNull Integer year) {
        return seasonRepository.existsByLeagueIdAndYearAndCurrentTrue(leagueId, year);
    }

    @Transactional
    public Season attachSeason(Season season) {
        return entityManager.merge(season); // Merge will attach a detached entity
    }
}