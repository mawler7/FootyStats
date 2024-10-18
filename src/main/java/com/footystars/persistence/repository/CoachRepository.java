package com.footystars.persistence.repository;

import com.footystars.model.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {


    @Query("SELECT c FROM Coach c JOIN c.career career WHERE c.team.teamId = :clubId AND career.endDate IS NULL")
    Optional<Coach> findCurrentCoachByClubId(Long clubId);
}
