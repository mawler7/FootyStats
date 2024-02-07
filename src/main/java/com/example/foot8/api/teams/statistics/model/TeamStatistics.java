package com.example.foot8.api.teams.statistics.model;


import com.example.foot8.api.teams.statistics.model.biggest.Biggest;
import com.example.foot8.api.teams.statistics.model.cards.Cards;
import com.example.foot8.api.teams.statistics.model.cleansheets.CleanSheet;
import com.example.foot8.api.teams.statistics.model.failed_to_score.FailedToScore;
import com.example.foot8.api.teams.statistics.model.fixtures.Fixtures;
import com.example.foot8.api.teams.statistics.model.goals.Goals;
import com.example.foot8.api.teams.statistics.model.league.League;
import com.example.foot8.api.teams.statistics.model.lineups.Lineup;
import com.example.foot8.api.teams.statistics.model.penalty.Penalty;
import com.example.foot8.api.teams.statistics.model.team.Team;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.Embedded;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TeamStatistics {

    @Embedded
    private Biggest biggest;

    private String form;

    @Embedded
    @JsonProperty("cards")
    private Cards cards;

    @Embedded
    @JsonProperty("clean_sheet")
    private CleanSheet cleanSheet;

    @Embedded
    @JsonProperty("failed_to_score")
    private FailedToScore failedToScore;

    @Embedded
    @JsonProperty("goals")
    private Goals goals;

    @Embedded
    private Fixtures fixtures;

    @Embedded
    private League league;

    @Embedded
    private List<Lineup> lineups;

    @Embedded
    private Penalty penalty;

    @Embedded
    private Team team;

}
