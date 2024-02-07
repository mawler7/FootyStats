package com.example.foot8.persistence.repository;

import com.example.foot8.persistence.entities.fixtures.events.FixtureEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixtureEventEntityRepository extends JpaRepository<FixtureEventEntity, Long> {
}