package com.footystars.persistence.repository;

import com.footystars.model.entity.Prediction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {


}
