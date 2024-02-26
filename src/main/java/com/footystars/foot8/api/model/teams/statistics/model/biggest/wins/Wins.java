package com.footystars.foot8.api.model.teams.statistics.model.biggest.wins;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Wins implements Serializable {

    @JsonProperty("away")
    private String away;

    @JsonProperty("home")
    private String home;
}
