package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entities.coachs.season.CoachSeason;
import com.footystars.foot8.persistence.repository.CoachSeasonRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CoachSeasonService {
    private final CoachSeasonRepository coachSeasonRepository;

    @Transactional
    public CoachSeason save(@NotNull CoachSeason coachSeason) {
        return coachSeasonRepository.save(coachSeason);
    }
}
