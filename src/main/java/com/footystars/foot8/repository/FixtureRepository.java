package com.footystars.foot8.repository;


import com.footystars.foot8.buisness.model.entity.Fixture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixtureRepository extends JpaRepository<Fixture, Long> {
}