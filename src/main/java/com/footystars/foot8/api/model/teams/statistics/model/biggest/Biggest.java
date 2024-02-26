package com.footystars.foot8.api.model.teams.statistics.model.biggest;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.teams.statistics.model.biggest.goals.BiggestGoals;
import com.footystars.foot8.api.model.teams.statistics.model.biggest.loses.Loses;
import com.footystars.foot8.api.model.teams.statistics.model.biggest.streak.Streak;
import com.footystars.foot8.api.model.teams.statistics.model.biggest.wins.Wins;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

