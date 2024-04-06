package com.footystars.foot8.repository;

import com.footystars.foot8.buisness.model.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
}
