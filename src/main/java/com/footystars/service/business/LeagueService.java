package com.footystars.service.business;


import com.footystars.model.api.Leagues;
import com.footystars.model.entity.League;
import com.footystars.persistence.mapper.LeagueMapper;
import com.footystars.persistence.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final LeagueMapper leagueMapper;

    @Transactional
    public void fetchLeague(@NotNull Leagues.LeagueDto leagueDto) {

        var seasons = leagueDto.getSeasons();
        seasons.forEach(s -> {
            var optionalLeague = leagueRepository.findByLeagueIdAndSeason(leagueDto.getInfo().getLeagueId(), s.getYear());
            if (optionalLeague.isEmpty()) {
                var league = leagueMapper.toEntity(leagueDto);
                league.setSeason(s);
                leagueRepository.save(league);
            } else {
                var league = optionalLeague.get();
                leagueMapper.partialUpdate(leagueDto, league);
                leagueRepository.save(league);
            }
        });
    }

    @Transactional(readOnly = true)
    public List<League> findByLeagueId(Long leagueId) {
        return leagueRepository.findByLeagueId(leagueId);
    }

    @Transactional(readOnly = true)
    public Optional<Integer> findCurrentSeasonByLeagueId(Long leagueId) {
        return leagueRepository.findCurrentSeasonById(leagueId, Boolean.TRUE);
    }

    public Optional<League> findByLeagueIdAndSeason(Long leagueId, Integer seasonYear) {
        return leagueRepository.findByLeagueIdAndSeason(leagueId, seasonYear);
    }

    @Transactional
    public void save(League league) {
        leagueRepository.save(league);
    }

}