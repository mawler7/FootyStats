package com.footystars.foot8.persistence.repository;

import com.footystars.foot8.persistence.entity.coachs.Coach;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoachRepository extends JpaRepository<Coach, Long> {
}
