package com.footystars.persistence.repository;

import com.footystars.model.entity.RequestLimiter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestLimiterRepository extends JpaRepository<RequestLimiter, Long> {
}
