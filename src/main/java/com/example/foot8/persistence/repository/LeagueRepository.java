package com.example.foot8.persistence.repository;

import com.example.foot8.persistence.entities.LeagueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeagueRepository extends JpaRepository<LeagueEntity, Long> {


}
