package com.example.foot8.api.league.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Seasons {

    private Long id;

    private Integer year;

    @JsonProperty("start")
    private String startDate;

    @JsonProperty("end")
    private String endDate;

    private boolean current;
}
