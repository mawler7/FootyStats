package com.footystars.foot8.repository;

import com.footystars.foot8.business.model.entity.RequestLimiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestLimiterRepository extends JpaRepository<RequestLimiter, Long> {
}
