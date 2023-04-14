package com.example.foot8.service;

import com.example.foot8.persistence.entities.TeamEntity;
import com.example.foot8.persistence.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public void save(TeamEntity teamEntity) {
        teamRepository.save(teamEntity);
    }

    public Optional<TeamEntity> findById(Long id) {
        return teamRepository.findById(id);
    }


}
