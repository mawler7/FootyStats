package com.footystars.foot8.api.model.predictions.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.predictions.response.comparison.Att;
import com.footystars.foot8.api.model.predictions.response.comparison.Def;
import com.footystars.foot8.api.model.predictions.response.comparison.Form;
import com.footystars.foot8.api.model.predictions.response.comparison.Goals;
import com.footystars.foot8.api.model.predictions.response.comparison.H2H;
import com.footystars.foot8.api.model.predictions.response.comparison.PoissonDistribution;
import com.footystars.foot8.api.model.predictions.response.comparison.Total;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Comparison implements Serializable {

    @Embedded
    private Form form;

    @Embedded
    private Att att;

    @Embedded
    private Def def;

    @JsonProperty("poisson_distribution")
    @Embedded
    private PoissonDistribution poissonDistribution;

    @Embedded
    private H2H h2h;

    @Embedded
    private Goals goals;

    @Embedded
    private Total total;

}
