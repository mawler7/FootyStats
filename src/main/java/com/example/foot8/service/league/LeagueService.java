package com.example.foot8.service.league;

import com.example.foot8.api.league.model.League;
import com.example.foot8.persistence.entities.countries.CountryEntity;
import com.example.foot8.persistence.entities.leagues.LeagueEntity;
import com.example.foot8.persistence.repository.LeagueRepository;
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

    @Transactional
    public LeagueEntity saveOrUpdateLeague(@NotNull League league, CountryEntity countryEntity, String leagueType) {
        return leagueRepository.findById(league.getId())
                .orElseGet(() -> {
                    LeagueEntity entity = LeagueEntity.builder()
                            .id(league.getId())
                            .name(league.getName())
                            .type(leagueType)
                            .logo(league.getLogo())
                            .country(countryEntity)
                            .build();
                    return leagueRepository.save(entity);
                });
    }

    public Optional<LeagueEntity> findById(Long id) {
        return leagueRepository.findById(id);
    }

    public List<Long> findAllIds() {
      return   leagueRepository.findAll().stream().map(LeagueEntity::getId).toList();
    }

    public void save(LeagueEntity leagueEntity) {
        leagueRepository.save(leagueEntity);
    }

}