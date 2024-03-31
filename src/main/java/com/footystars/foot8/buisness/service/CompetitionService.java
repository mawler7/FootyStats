package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entity.competitions.Competition;
import com.footystars.foot8.persistence.repository.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompetitionService {

    private static final Logger logger = LoggerFactory.getLogger(CompetitionService.class);

    private final CompetitionRepository competitionRepository;

    public void createCompetition(@NotNull Competition competition) {
        var id = competition.getLeague().getId();
        int year = competition.getSeason().getYear();
        var optionalCompetition = getByLeagueAndSeasonYear(id, year);
        if (optionalCompetition.isEmpty()) {
            save(competition);
            logger.info("Fetched competition {} with season {}",competition.getLeague().getName(), year);
        } else {
            logger.warn("Competition already exists");
        }
    }

    public Optional<Competition> getByLeagueAndSeasonYear(Long leagueId, int year) {
        return competitionRepository.findByLeagueIdAndSeasonYear(leagueId, year);
    }


    public Competition save(@NotNull Competition competition) {
        return competitionRepository.save(competition);
    }

}



