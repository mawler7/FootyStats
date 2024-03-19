package com.footystars.foot8.api.model.teams.statistics.statistic.biggest;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.teams.statistics.statistic.biggest.goals.Goals;
import com.footystars.foot8.api.model.teams.statistics.statistic.biggest.loses.Loses;
import com.footystars.foot8.api.model.teams.statistics.statistic.biggest.streak.Streak;
import com.footystars.foot8.api.model.teams.statistics.statistic.biggest.wins.Wins;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Biggest implements Serializable {

    private Goals goals;
    private Loses loses;
    private Streak streak;
    private Wins wins;

}

