package com.example.foot8.persistence.entities.teams.statistics;

import com.example.foot8.api.teams.statistics.model.biggest.Biggest;
import com.example.foot8.api.teams.statistics.model.cards.Cards;
import com.example.foot8.api.teams.statistics.model.cleansheets.CleanSheet;
import com.example.foot8.api.teams.statistics.model.failed_to_score.FailedToScore;
import com.example.foot8.api.teams.statistics.model.fixtures.Fixtures;
import com.example.foot8.api.teams.statistics.model.goals.Goals;
import com.example.foot8.api.teams.statistics.model.league.League;
import com.example.foot8.api.teams.statistics.model.lineups.Lineup;
import com.example.foot8.api.teams.statistics.model.penalty.Penalty;
import com.example.foot8.persistence.entities.teams.TeamEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
@Entity
@Table(name="team_statistics")
@AllArgsConstructor
public class TeamStatisticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String form;

    @Embedded
    @JsonProperty("biggest")
    @AttributeOverride(name = "biggestGoals.against.home", column = @Column(name = "biggest_goals_conceded_home"))
    @AttributeOverride(name = "biggestGoals.against.away", column = @Column(name = "biggest_goals_conceded_away"))
    @AttributeOverride(name = "biggestGoals.against.total", column = @Column(name = "biggest_goals_conceded_total"))
    @AttributeOverride(name = "biggestGoals._for.home", column = @Column(name = "biggest_goals_scored_home"))
    @AttributeOverride(name = "biggestGoals._for.away", column = @Column(name = "biggest_goals_scored_away"))
    @AttributeOverride(name = "biggestGoals._for.total", column = @Column(name = "biggest_goals_scored_total"))
    @AttributeOverride(name = "biggestLost.home", column = @Column(name = "biggest_loses_home"))
    @AttributeOverride(name = "biggestLost.away", column = @Column(name = "biggest_loses_away"))
    @AttributeOverride(name = "biggestStreak.draws", column = @Column(name = "biggest_draws_streak"))
    @AttributeOverride(name = "biggestStreak.loses", column = @Column(name = "biggest_loses_streak"))
    @AttributeOverride(name = "biggestStreak.wins", column = @Column(name = "biggest_wins_streak"))
    @AttributeOverride(name = "biggestWin.home", column = @Column(name = "biggest_wins_home"))
    @AttributeOverride(name = "biggestWin.away", column = @Column(name = "biggest_wins_away"))
    private Biggest biggest;

    @Embedded
    @JsonProperty(namespace = "cards")
    @AttributeOverride(name = "red.from0To15.percentage", column = @Column(name = "red_cards_0_15_percentage"))
    @AttributeOverride(name = "red.from0To15.total", column = @Column(name = "red_cards_0_15_total"))
    @AttributeOverride(name = "red.from16To30.percentage", column = @Column(name = "red_cards_16_30_percentage"))
    @AttributeOverride(name = "red.from16To30.total", column = @Column(name = "red_cards_16_30_total"))
    @AttributeOverride(name = "red.from31To45.percentage", column = @Column(name = "red_cards_31_45_percentage"))
    @AttributeOverride(name = "red.from31To45.total", column = @Column(name = "red_cards_31_45_total"))
    @AttributeOverride(name = "red.from46To60.percentage", column = @Column(name = "red_cards_46_60_percentage"))
    @AttributeOverride(name = "red.from46To60.total", column = @Column(name = "red_cards_46_60_total"))
    @AttributeOverride(name = "red.from61To75.percentage", column = @Column(name = "red_cards_61_75_percentage"))
    @AttributeOverride(name = "red.from61To75.total", column = @Column(name = "red_cards_61_75_total"))
    @AttributeOverride(name = "red.from76To90.percentage", column = @Column(name = "red_cards_76_90_percentage"))
    @AttributeOverride(name = "red.from76To90.total", column = @Column(name = "red_cards_76_90_total"))
    @AttributeOverride(name = "red.from91To105.percentage", column = @Column(name = "red_cards_91_105_percentage"))
    @AttributeOverride(name = "red.from91To105.total", column = @Column(name = "red_cards_91_105_total"))
    @AttributeOverride(name = "red.from106To120.percentage", column = @Column(name = "red_cards_106_120_percentage"))
    @AttributeOverride(name = "red.from106To120.total", column = @Column(name = "red_cards_106_120_total"))
    @AttributeOverride(name = "yellow.from0To15.percentage", column = @Column(name = "yellow_cards_0_15_percentage"))
    @AttributeOverride(name = "yellow.from0To15.total", column = @Column(name = "yellow_cards_0_15_total"))
    @AttributeOverride(name = "yellow.from16To30.percentage", column = @Column(name = "yellow_cards_16_30_percentage"))
    @AttributeOverride(name = "yellow.from16To30.total", column = @Column(name = "yellow_cards_16_30_total"))
    @AttributeOverride(name = "yellow.from31To45.percentage", column = @Column(name = "yellow_cards_31_45_percentage"))
    @AttributeOverride(name = "yellow.from31To45.total", column = @Column(name = "yellow_cards_31_45_total"))
    @AttributeOverride(name = "yellow.from46To60.percentage", column = @Column(name = "yellow_cards_46_60_percentage"))
    @AttributeOverride(name = "yellow.from46To60.total", column = @Column(name = "yellow_cards_46_60_total"))
    @AttributeOverride(name = "yellow.from61To75.percentage", column = @Column(name = "yellow_cards_61_75_percentage"))
    @AttributeOverride(name = "yellow.from61To75.total", column = @Column(name = "yellow_cards_61_75_total"))
    @AttributeOverride(name = "yellow.from76To90.percentage", column = @Column(name = "yellow_cards_76_90_percentage"))
    @AttributeOverride(name = "yellow.from76To90.total", column = @Column(name = "yellow_cards_76_90_total"))
    @AttributeOverride(name = "yellow.from91To105.percentage", column = @Column(name = "yellow_cards_91_105_percentage"))
    @AttributeOverride(name = "yellow.from91To105.total", column = @Column(name = "yellow_cards_91_105_total"))
    @AttributeOverride(name = "yellow.from106To120.percentage", column = @Column(name = "yellow_cards_106_120_percentage"))
    @AttributeOverride(name = "yellow.from106To120.total", column = @Column(name = "yellow_cards_106_120_total"))
    private Cards cards;

    @Embedded
    @JsonProperty(namespace = "clean_sheet")
    @AttributeOverride(name = "home", column = @Column(name = "clean_sheet_home"))
    @AttributeOverride(name = "away", column = @Column(name = "clean_sheet_away"))
    @AttributeOverride(name = "total", column = @Column(name = "clean_sheet_total"))
    private CleanSheet cleanSheet;

    @Embedded
    @JsonProperty(namespace = "failed_to_score")
    @AttributeOverride(name = "home", column = @Column(name = "failed_to_score_home"))
    @AttributeOverride(name = "away", column = @Column(name = "failed_to_score_away"))
    @AttributeOverride(name = "total", column = @Column(name = "failed_to_score_total"))
    private FailedToScore failedToScore;

    @Embedded
    @JsonProperty(namespace = "goals")
    @AttributeOverride(name = "_for.average.away", column = @Column(name = "goals_scored_average_away"))
    @AttributeOverride(name = "_for.average.home", column = @Column(name = "goals_scored_average_home"))
    @AttributeOverride(name = "_for.average.total", column = @Column(name = "goals_scored_average_total"))
    @AttributeOverride(name = "_for.minute.from0To15.percentage", column = @Column(name = "goals_scored_0_15_percentage"))
    @AttributeOverride(name = "_for.minute.from0To15.total", column = @Column(name = "goals_scored_0_15_total"))
    @AttributeOverride(name = "_for.minute.from16To30.percentage", column = @Column(name = "goals_scored_16_30_total"))
    @AttributeOverride(name = "_for.minute.from16To30.total", column = @Column(name = "goals_scored_16_30_percentage"))
    @AttributeOverride(name = "_for.minute.from31To45.percentage", column = @Column(name = "goals_scored_31_45_percentage"))
    @AttributeOverride(name = "_for.minute.from31To45.total", column = @Column(name = "goals_scored_31_45_total"))
    @AttributeOverride(name = "_for.minute.from46To60.percentage", column = @Column(name = "goals_scored_46_60_percentage"))
    @AttributeOverride(name = "_for.minute.from46To60.total", column = @Column(name = "goals_scored_46_60_total"))
    @AttributeOverride(name = "_for.minute.from61To75.percentage", column = @Column(name = "goals_scored_61_75_percentage"))
    @AttributeOverride(name = "_for.minute.from61To75.total", column = @Column(name = "goals_scored_61_75_total"))
    @AttributeOverride(name = "_for.minute.from76To90.percentage", column = @Column(name = "goals_scored_76_90_percentage"))
    @AttributeOverride(name = "_for.minute.from76To90.total", column = @Column(name = "goals_scored_76_90_total"))
    @AttributeOverride(name = "_for.minute.from91To105.percentage", column = @Column(name = "goals_scored_91_105_percentage"))
    @AttributeOverride(name = "_for.minute.from91To105.total", column = @Column(name = "goals_scored_91_105_total"))
    @AttributeOverride(name = "_for.minute.from106To120.percentage", column = @Column(name = "goals_scored_106_120_percentage"))
    @AttributeOverride(name = "_for.minute.from106To120.total", column = @Column(name = "goals_scored_106_120_total"))
    @AttributeOverride(name = "_for.total.home", column = @Column(name = "goals_scored_total_home"))
    @AttributeOverride(name = "_for.total.away", column = @Column(name = "goals_scored_total_away"))
    @AttributeOverride(name = "_for.total.total", column = @Column(name = "goals_scored_total"))
    @AttributeOverride(name = "against.average.away", column = @Column(name = "goals_conceded_average_away"))
    @AttributeOverride(name = "against.average.home", column = @Column(name = "goals_conceded_average_home"))
    @AttributeOverride(name = "against.average.total", column = @Column(name = "goals_conceded_average_total"))
    @AttributeOverride(name = "against.minute.from0To15.percentage", column = @Column(name = "goals_conceded_0_15_percentage"))
    @AttributeOverride(name = "against.minute.from0To15.total", column = @Column(name = "goals_conceded_0_15_total"))
    @AttributeOverride(name = "against.minute.from16To30.percentage", column = @Column(name = "goals_conceded_16_30_total"))
    @AttributeOverride(name = "against.minute.from16To30.total", column = @Column(name = "goals_conceded_16_30_percentage"))
    @AttributeOverride(name = "against.minute.from31To45.percentage", column = @Column(name = "goals_conceded_31_45_percentage"))
    @AttributeOverride(name = "against.minute.from31To45.total", column = @Column(name = "goals_conceded_31_45_total"))
    @AttributeOverride(name = "against.minute.from46To60.percentage", column = @Column(name = "goals_conceded_46_60_percentage"))
    @AttributeOverride(name = "against.minute.from46To60.total", column = @Column(name = "goals_conceded_46_60_total"))
    @AttributeOverride(name = "against.minute.from61To75.percentage", column = @Column(name = "goals_conceded_61_75_percentage"))
    @AttributeOverride(name = "against.minute.from61To75.total", column = @Column(name = "goals_conceded_61_75_total"))
    @AttributeOverride(name = "against.minute.from76To90.percentage", column = @Column(name = "goals_conceded_76_90_percentage"))
    @AttributeOverride(name = "against.minute.from76To90.total", column = @Column(name = "goals_conceded_76_90_total"))
    @AttributeOverride(name = "against.minute.from91To105.percentage", column = @Column(name = "goals_conceded_91_105_percentage"))
    @AttributeOverride(name = "against.minute.from91To105.total", column = @Column(name = "goals_conceded_91_105_total"))
    @AttributeOverride(name = "against.minute.from106To120.percentage", column = @Column(name = "goals_conceded_106_120_percentage"))
    @AttributeOverride(name = "against.minute.from106To120.total", column = @Column(name = "goals_conceded_106_120_total"))
    @AttributeOverride(name = "against.total.home", column = @Column(name = "goals_conceded_total_home"))
    @AttributeOverride(name = "against.total.away", column = @Column(name = "goals_conceded_total_away"))
    @AttributeOverride(name = "against.total.total", column = @Column(name = "goals_conceded_total"))
    private Goals goals;

    @Embedded
    @AttributeOverride(name = "draws.home", column = @Column(name = "draws_home"))
    @AttributeOverride(name = "draws.away", column = @Column(name = "draws_away"))
    @AttributeOverride(name = "draws.total", column = @Column(name = "draws_total"))
    @AttributeOverride(name = "loses.home", column = @Column(name = "loses_home"))
    @AttributeOverride(name = "loses.away", column = @Column(name = "loses_away"))
    @AttributeOverride(name = "loses.total", column = @Column(name = "loses_total"))
    @AttributeOverride(name = "played.home", column = @Column(name = "played_home"))
    @AttributeOverride(name = "played.away", column = @Column(name = "played_away"))
    @AttributeOverride(name = "played.total", column = @Column(name = "played_total"))
    @AttributeOverride(name = "wins.home", column = @Column(name = "wins_home"))
    @AttributeOverride(name = "wins.away", column = @Column(name = "wins_away"))
    @AttributeOverride(name = "wins.total", column = @Column(name = "wins_total"))
    private Fixtures fixtures;

    @Embedded
    private League league;


    @ElementCollection
    @CollectionTable(name = "lineups", joinColumns = @JoinColumn(name = "team_statistics_id"))
    private List<Lineup> lineups;

    @Embedded
    private Penalty penalty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        this.lastUpdated = LocalDateTime.now();
    }

}