package com.example.foot8.api.teams.statistics.model.biggest;


import com.example.foot8.api.teams.statistics.model.biggest.goals.BiggestGoals;
import com.example.foot8.api.teams.statistics.model.biggest.loses.Loses;
import com.example.foot8.api.teams.statistics.model.biggest.streak.Streak;
import com.example.foot8.api.teams.statistics.model.biggest.wins.Wins;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Biggest implements Serializable {

    @Embedded
    @JsonProperty("goals")
    private BiggestGoals biggestGoals;

    @Embedded
    @JsonProperty("loses")
    private Loses biggestLost;

    @Embedded
    @JsonProperty("streak")
    private Streak biggestStreak;

    @Embedded
    @JsonProperty("wins")
    private Wins biggestWin;

}

