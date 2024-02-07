package com.example.foot8.persistence.repository;

import com.example.foot8.persistence.entities.fixtures.statistics.FixtureStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixtureStatisticsEntityRepository extends JpaRepository<FixtureStatisticsEntity, Long> {
}