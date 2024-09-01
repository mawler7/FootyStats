package com.footystars.persistence.repository;

import com.footystars.persistence.entity.Coach;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoachRepository extends JpaRepository<Coach, Long> {
}
