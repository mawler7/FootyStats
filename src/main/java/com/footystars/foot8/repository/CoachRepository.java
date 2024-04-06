package com.footystars.foot8.repository;

import com.footystars.foot8.buisness.model.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoachRepository extends JpaRepository<Coach, Long> {
}
