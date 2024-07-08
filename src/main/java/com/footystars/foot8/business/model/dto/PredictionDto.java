package com.footystars.foot8.business.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.predictions.response.Comparison;
import com.footystars.foot8.api.model.predictions.response.predictions.PredictionDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PredictionDto implements Serializable {
   private PredictionDetails predictions;
   private Comparison comparison;
   private List<FixtureDto> h2h;
}