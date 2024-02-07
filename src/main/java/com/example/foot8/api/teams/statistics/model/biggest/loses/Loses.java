package com.example.foot8.api.teams.statistics.model.biggest.loses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
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
