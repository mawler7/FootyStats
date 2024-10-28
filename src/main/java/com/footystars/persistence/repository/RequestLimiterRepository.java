package com.footystars.persistence.repository;

import com.footystars.model.entity.RequestLimiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLimiterRepository extends JpaRepository<RequestLimiter, Long> {
}
