package com.footystars.foot8.persistence.repository;


import com.footystars.foot8.persistence.entities.fixtures.fixture.Fixture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixtureRepository extends JpaRepository<Fixture, Long> {
}