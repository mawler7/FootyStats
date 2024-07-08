package com.footystars.foot8.api.model.leagues.league.season;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.footystars.foot8.api.model.leagues.league.coverage.Coverage;
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
public class SeasonApi implements Serializable {
    private int year;
    @JsonProperty("start")
    private String startDate;
    @JsonProperty("end")
    private String endDate;
    private Boolean current;
    private Coverage coverage;


}
