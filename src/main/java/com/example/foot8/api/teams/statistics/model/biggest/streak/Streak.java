package com.example.foot8.api.teams.statistics.model.biggest.streak;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class Streak implements Serializable {

    private int draws;
    private int loses;
    private int wins;
}
