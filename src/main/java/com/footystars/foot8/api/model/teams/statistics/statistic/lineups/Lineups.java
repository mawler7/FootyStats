package com.footystars.foot8.api.model.teams.statistics.statistic.lineups;

import com.footystars.foot8.api.model.teams.statistics.statistic.lineups.lineup.Lineup;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
public class Lineups implements Serializable {

    private List<Lineup> lineupList;
}
