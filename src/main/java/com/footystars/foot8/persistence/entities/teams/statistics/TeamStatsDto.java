package com.footystars.foot8.persistence.entities.teams.statistics;


import com.footystars.foot8.api.model.teams.info.model.TeamDto;
import com.footystars.foot8.api.model.teams.statistics.model.biggest.Biggest;
import com.footystars.foot8.api.model.teams.statistics.model.cards.Cards;
import com.footystars.foot8.api.model.teams.statistics.model.cleansheets.CleanSheet;
import com.footystars.foot8.api.model.teams.statistics.model.failed2score.FailedToScore;
import com.footystars.foot8.api.model.teams.statistics.model.fixtures.TeamStatsFixtures;
import com.footystars.foot8.api.model.teams.statistics.model.goals.Goals;
import com.footystars.foot8.api.model.teams.statistics.model.league.LeagueResponse;
import com.footystars.foot8.api.model.teams.statistics.model.lineups.Lineup;
import com.footystars.foot8.api.model.teams.statistics.model.penalty.Penalty;
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
public class TeamStatsDto implements Serializable {
    private Long id;
    private String form;
    private Biggest biggest;
    private Cards cards;
    private CleanSheet cleanSheet;
    private FailedToScore failedToScore;
    private Goals goals;
    private TeamStatsFixtures fixtures;
    private LeagueResponse league;
    private Penalty penalty;
    private List<Lineup> lineups;
    private TeamDto team;
    private LocalDateTime lastUpdated;
}