package com.footystars.foot8.api.model.standings.standing.league;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.standings.standing.Standing;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class StandingLeague implements Serializable {
    private Long id;
    private String name;
    private String country;
    private String logo;
    private String flag;
    private Integer season;
    private ArrayList<ArrayList<Standing>> standings;
}

