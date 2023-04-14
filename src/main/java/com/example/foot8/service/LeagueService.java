package com.example.foot8.service;

import com.example.foot8.persistence.entities.LeagueEntity;
import com.example.foot8.persistence.repository.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeagueService {

    private final LeagueRepository leagueRepository;

    public void save(LeagueEntity leagueEntity) {
        leagueRepository.save(leagueEntity);
    }

    public Optional<LeagueEntity> findById(Long id) {
        return leagueRepository.findById(id);
    }

}
