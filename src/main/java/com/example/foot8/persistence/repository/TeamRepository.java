package com.example.foot8.persistence.repository;

import com.example.foot8.buisness.match.model.TeamDto;
import com.example.foot8.persistence.entities.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Long> {
}
