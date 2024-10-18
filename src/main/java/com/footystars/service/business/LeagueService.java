package com.footystars.service.business;


import com.footystars.model.api.Leagues;
import com.footystars.model.dto.LeagueDto;
import com.footystars.model.entity.League;
import com.footystars.persistence.mapper.FixtureMapper;
import com.footystars.persistence.mapper.LeagueMapper;
import com.footystars.persistence.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
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
                var flag = leagueDto.getCountry().getCountryFlag();
                league.getInfo().setFlag(flag);
                leagueRepository.save(league);
            }
        });
    }

    @Transactional(readOnly = true)
    public List<League> findByLeagueId(Long leagueId) {
        return leagueRepository.findByLeagueId(leagueId);
    }

    @Transactional(readOnly = true)
    public Optional<Integer> findCurrentNotEndedSeasonByLeagueId(Long leagueId) {
        var today = ZonedDateTime.now().toLocalDate().toString();
        return leagueRepository.findCurrentSeasonById(leagueId, Boolean.TRUE, today);
    }

    @Transactional(readOnly = true)
    public Optional<Integer> findCurrentSeasonByLeagueId(Long leagueId) {
        return leagueRepository.findCurrentSeasonByLeagueId(leagueId, Boolean.TRUE);
    }


    public Optional<League> findByLeagueIdAndSeason(Long leagueId, Integer seasonYear) {
        return leagueRepository.findByLeagueIdAndSeason(leagueId, seasonYear);
    }

    @Transactional
    public void save(League league) {
        leagueRepository.save(league);
    }

    public List<LeagueDto> findCurrentSeasonLeagues() {
        var currentSeasonLeagues = leagueRepository.findCurrentSeasonLeagues(Boolean.TRUE);

        return currentSeasonLeagues.stream().map(leagueMapper::toDto).toList();
    }

    public LeagueDto findLeagueSeasonsByLeagueId(Long leagueId) {
        var optionalSeason = leagueRepository.findCurrentSeasonByLeagueId(leagueId, Boolean.TRUE);
        if (optionalSeason.isPresent()) {
            var season = optionalSeason.get();
            var optionalLeague = findByLeagueIdAndSeason(leagueId, season);
            if (optionalLeague.isPresent()) {
                var league = optionalLeague.get();
                return leagueMapper.toDto(league);
            }
        }
        return null;
    }

}