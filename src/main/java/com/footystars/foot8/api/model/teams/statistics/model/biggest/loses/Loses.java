package com.footystars.foot8.api.model.teams.statistics.model.biggest.loses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Loses implements Serializable {

    @JsonProperty(namespace = "away")
    private String away;

    @JsonProperty(namespace = "home")
    private String home;
}
