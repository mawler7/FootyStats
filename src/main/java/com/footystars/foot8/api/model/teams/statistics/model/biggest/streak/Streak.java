package com.footystars.foot8.api.model.teams.statistics.model.biggest.streak;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Streak implements Serializable {

    private int draws;
    private int loses;
    private int wins;
}
