package com.footystars.foot8.api.model.teams.statistics.statistic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.teams.statistics.statistic.biggest.Biggest;
import com.footystars.foot8.api.model.teams.statistics.statistic.cards.Cards;
import com.footystars.foot8.api.model.teams.statistics.statistic.clean_sheets.CleanSheet;
import com.footystars.foot8.api.model.teams.statistics.statistic.failed_to_score.FailedToScore;
import com.footystars.foot8.api.model.teams.statistics.statistic.fixtures.Fixtures;
import com.footystars.foot8.api.model.teams.statistics.statistic.goals.Goals;
import com.footystars.foot8.api.model.teams.statistics.statistic.league.League;
import com.footystars.foot8.api.model.teams.statistics.statistic.lineups.lineup.Lineup;
import com.footystars.foot8.api.model.teams.statistics.statistic.penalty.Penalty;
import com.footystars.foot8.business.model.dto.ClubDto;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamStatisticApi implements Serializable {

    private Biggest biggest;
    private Cards cards;
    @JsonProperty("clean_sheet")
    private CleanSheet cleanSheet;
    @JsonProperty("failed_to_score")
    private FailedToScore failedToScore;
    private Fixtures fixtures;
    private String form;
    private Goals goals;
    private League league;
    private List<Lineup> lineups;
    private Penalty penalty;
    private ClubDto team;

}
