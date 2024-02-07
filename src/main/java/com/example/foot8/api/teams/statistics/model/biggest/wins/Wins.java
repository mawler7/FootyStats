package com.example.foot8.api.teams.statistics.model.biggest.wins;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
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
