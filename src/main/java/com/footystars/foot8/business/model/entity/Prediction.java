package com.footystars.foot8.business.model.entity;

import com.footystars.foot8.api.model.predictions.response.Comparison;
import com.footystars.foot8.api.model.predictions.response.predictions.PredictionDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "predictions")
public class Prediction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Embedded
    private PredictionDetails predictions;

    @Embedded
    private Comparison comparison;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "prediction", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Fixture> fixturesH2H;

}
