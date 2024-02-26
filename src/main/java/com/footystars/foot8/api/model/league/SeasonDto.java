package com.footystars.foot8.api.model.league;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SeasonDto implements Serializable {

    @JsonProperty("year")
    private Integer year;
    @JsonProperty("start")
    private String startDate;
    @JsonProperty("end")
    private String endDate;
    @JsonProperty("current")
    private Boolean isCurrent;

    @Embedded
    private Coverage coverage;

}