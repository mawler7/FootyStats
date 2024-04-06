package com.footystars.foot8.api.model.predictions.response.predictions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.predictions.response.predictions.info.Goals;
import com.footystars.foot8.api.model.predictions.response.predictions.info.Percent;
import com.footystars.foot8.api.model.predictions.response.predictions.info.Winner;
import jakarta.persistence.Embeddable;
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
@Embeddable
public class PredictionDetails implements Serializable {

    @Embedded
    private Winner winner;

    @JsonProperty("win_or_draw")
    private Boolean winOrDraw;

    @JsonProperty("under_over")
    private String underOver;

    @Embedded
    private Goals goals;

    private String advice;

    @Embedded
    private Percent percent;

}
