package com.footystars.foot8.api.model.teams.statistics.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.teams.statistics.model.biggest.Biggest;
import com.footystars.foot8.api.model.teams.statistics.model.cards.Cards;
import com.footystars.foot8.api.model.teams.statistics.model.cleansheets.CleanSheet;
import com.footystars.foot8.api.model.teams.statistics.model.failed2score.FailedToScore;
import com.footystars.foot8.api.model.teams.statistics.model.fixtures.TeamStatsFixtures;
import com.footystars.foot8.api.model.teams.statistics.model.goals.Goals;
import com.footystars.foot8.api.model.teams.statistics.model.league.LeagueResponse;
import com.footystars.foot8.api.model.teams.statistics.model.lineups.Lineup;
import com.footystars.foot8.api.model.teams.statistics.model.penalty.Penalty;
import com.footystars.foot8.api.model.teams.statistics.model.team.TeamDto;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TeamStatisticsDto implements Serializable {

    @Embedded
    private TeamDto team;

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
    private TeamStatsFixtures fixtures;

    @Embedded
    private LeagueResponse league;

    @Embedded
    private List<Lineup> lineups;

    @Embedded
    private Penalty penalty;

    private LocalDateTime lastUpdated;

}
