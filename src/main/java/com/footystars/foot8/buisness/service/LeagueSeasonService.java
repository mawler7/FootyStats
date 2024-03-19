package com.footystars.foot8.buisness.service;

import com.footystars.foot8.persistence.entities.leagues.seaon.LeagueSeason;
import com.footystars.foot8.persistence.entities.leagues.league.League;
import com.footystars.foot8.persistence.entities.leagues.seaon.LeagueSeasonDto;
import com.footystars.foot8.persistence.entities.leagues.seaon.LeagueSeasonMapper;
import com.footystars.foot8.persistence.repository.LeagueSeasonRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeagueSeasonService {

    private final LeagueSeasonRepository leagueSeasonRepository;
    private final LeagueSeasonMapper leagueSeasonMapper;

    @Transactional
    public void updateSeasons(@NotNull League league, @NotNull Set<LeagueSeasonDto> leagueSeasons) {
        var seasonYears = leagueSeasons.stream()
                .map(LeagueSeasonDto::getYear)
                .collect(Collectors.toSet());

        var existingSeasonYears = league.getSeasons().stream()
                .map(LeagueSeason::getYear)
                .collect(Collectors.toSet());

        if (seasonYears.size() != existingSeasonYears.size()) {
            var currentSeasonYear = leagueSeasons.stream()
                    .filter(LeagueSeasonDto::getCurrent)
                    .map(LeagueSeasonDto::getYear)
                    .findFirst()
                    .orElse(null);

            for (var seasonDto : leagueSeasons) {
                var year = seasonDto.getYear();
                boolean current = seasonDto.getCurrent();

                var existingSeason = league.getSeasons().stream()
                        .filter(season -> season.getYear() == year)
                        .findFirst()
                        .orElse(null);

                if (existingSeason == null) {
                    var newSeason = leagueSeasonMapper.toEntity(seasonDto);
                    newSeason.setLeague(league);

                    leagueSeasonRepository.saveAndFlush(newSeason);
                    league.getSeasons().add(newSeason);
                } else {
                    existingSeason.setCurrent(current);

                    if (current && currentSeasonYear != null && currentSeasonYear == year) {
                        existingSeason.setCurrent(false);
                    }

                    leagueSeasonRepository.saveAndFlush(existingSeason);
                }
            }
            league.getSeasons().removeIf(season -> !seasonYears.contains(season.getYear()));
        }
    }


    public Optional<LeagueSeason> getByLeagueIdAndSeason(Long leagueId, Integer season) {
       return leagueSeasonRepository.findByLeagueIdAndYear(leagueId, season);
    }


    public List<Integer> getLeagueSeasonsYears(Long leagueId) {
        return leagueSeasonRepository.findByLeagueId(leagueId).stream().map(LeagueSeason::getYear).toList();
    }


    public void save(LeagueSeason leagueSeason) {
        leagueSeasonRepository.save(leagueSeason);
    }

    public List<LeagueSeason> getByTeamId(Long teamId){
        return leagueSeasonRepository.findByTeamSeasonsTeamId(teamId);
    }

}