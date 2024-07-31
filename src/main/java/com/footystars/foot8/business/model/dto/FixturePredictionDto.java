package com.footystars.foot8.business.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FixturePredictionDto {
    private ZonedDateTime date;
    private String status;
    private String home;
    private String away;
    private String homePrediction;
    private String awayPrediction;
    private String underOver;
    private String advice;
}
