package com.footystars.foot8.api.model.predictions.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

public class PredictionApi implements Serializable {
    private PredictionDetails predictions;
    private Comparison comparison;
    private TeamsData teams;
    private List<H2HData> h2h;

}
