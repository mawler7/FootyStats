package com.footystars.foot8.api.model.predictions.response.predictions.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
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
public class Percent implements Serializable {

    @JsonProperty("home")
    private String percentage;

    @JsonProperty("draw")
    private String drawPercentage;

    @JsonProperty("away")
    private String awayPercentage;
}
