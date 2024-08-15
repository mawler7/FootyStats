package com.footystars.foot8.business.service;

import com.footystars.foot8.api.model.leagues.league.LeagueApi;
import com.footystars.foot8.business.model.dto.LeagueDto;
import com.footystars.foot8.business.model.entity.League;
import com.footystars.foot8.mapper.LeagueMapper;
import com.footystars.foot8.mapper.SeasonMapper;
import com.footystars.foot8.repository.LeagueRepository;
import com.footystars.foot8.repository.SeasonRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.footystars.foot8.utils.LogsNames.LEAGUE_FETCHED;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "leagues")
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final SeasonRepository seasonRepository;
    private final SeasonMapper seasonMapper;
    private final LeagueMapper leagueMapper;

    private final Logger logger = LoggerFactory.getLogger(LeagueService.class);

    @Transactional
    public void fetchLeague(@NotNull LeagueApi leagueApi) {
        var leagueDto = leagueMapper.apiToDto(leagueApi);
        var leagueEntity = leagueMapper.toEntity(leagueDto);

        var savedLeague = saveOrUpdateLeague(leagueEntity);

        leagueApi.getSeasons().forEach(seasonApi -> {
            var existingSeasonOpt = seasonRepository.findByYearAndLeagueId(seasonApi.getYear(), savedLeague.getId());
            if (existingSeasonOpt.isPresent()) {
                var existingSeason = existingSeasonOpt.get();
                seasonMapper.partialUpdate(seasonMapper.toDto(seasonApi), existingSeason);
                seasonRepository.save(existingSeason);
            } else {
                var seasonDto = seasonMapper.apiToDto(seasonApi);
                var seasonEntity = seasonMapper.toEntity(seasonDto);
                seasonEntity.setLeague(savedLeague);
                seasonRepository.save(seasonEntity);
            }
        });
        logger.info(LEAGUE_FETCHED, leagueApi.getLeagueInfo().getLeagueName());
    }

    @CacheEvict(allEntries = true)
    @Transactional
    public League saveOrUpdateLeague(@NotNull League league) {
        var existingLeagueOpt = leagueRepository.findById(league.getId());
        if (existingLeagueOpt.isPresent()) {
            var existingLeague = existingLeagueOpt.get();
            leagueMapper.partialUpdate(leagueMapper.toDto(league), existingLeague);
            return leagueRepository.save(existingLeague);
        } else {
            return leagueRepository.save(league);
        }
    }

    @Cacheable(key = "#id")
    @Transactional(readOnly = true)
    public Optional<League> findById(Long id) {
        return leagueRepository.findById(id);
    }

    public List<LeagueDto> findAllLeaguesDto() {
        return leagueRepository.findAll().stream().map(leagueMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<League> findAll() {
        return leagueRepository.findAll();
    }

}