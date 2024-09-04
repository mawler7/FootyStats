package com.footystars.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "team_statistics")
public class TeamStats implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "statistics", cascade = CascadeType.ALL)
    private Team team;

    @Embedded
    private Biggest biggest;

    @Embedded
    private Cards cards;

    @Embedded
    private CleanSheet cleanSheet;

    @Embedded
    private FailedToScore failedToScore;

    @Embedded
    private Fixtures fixtures;

    @Column(name = "form")
    private String form;

    @Embedded
    private Goals goals;

    @Embedded
    private LeagueInfo league;

    @ElementCollection
    @CollectionTable(name = "team_lineups", joinColumns = @JoinColumn(name = "statistics_id"))
    private List<Lineup> lineups;

    @Embedded
    private Penalty penalty;

    @Embeddable
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Biggest implements Serializable {

        @Embedded
        private GoalsInfo goals;

        @Embedded
        private Streak streak;

        @Embedded
        @AttributeOverride(name = "home", column = @Column(name = "biggest_loses_home"))
        @AttributeOverride(name = "away", column = @Column(name = "biggest_loses_away"))
        private HomeAway loses;

        @Embedded
        @AttributeOverride(name = "home", column = @Column(name = "biggest_wins_home"))
        @AttributeOverride(name = "away", column = @Column(name = "biggest_wins_away"))
        private HomeAway wins;

        @Embeddable
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class GoalsInfo implements Serializable {

            @Embedded
            @AttributeOverride(name = "home", column = @Column(name = "biggest_goals_against_home"))
            @AttributeOverride(name = "away", column = @Column(name = "biggest_goals_against_away"))
            private HomeAway against;

            @Embedded
            @AttributeOverride(name = "home", column = @Column(name = "biggest_goals_for_home"))
            @AttributeOverride(name = "away", column = @Column(name = "biggest_goals_for_away"))
            @JsonProperty("for")
            private HomeAway forGoals;
        }

        @Embeddable
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class HomeAway implements Serializable {
            private String home;
            private String away;
        }

    }


    @Embeddable
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Streak implements Serializable {
        @AttributeOverride(name = "draws", column = @Column(name = "biggest_draws_streak"))
        private Integer draws;
        @AttributeOverride(name = "loses", column = @Column(name = "biggest_loses_streak"))
        private Integer loses;
        @AttributeOverride(name = "wins", column = @Column(name = "biggest_wins_streak"))
        private Integer wins;
    }


    @Embeddable
    @Getter
    @Setter
    public static class CleanSheet implements Serializable {

        @Column(name = "clean_sheet_away")
        private Integer away;

        @Column(name = "clean_sheet_home")
        private Integer home;

        @Column(name = "clean_sheet_total")
        private Integer total;
    }

    @Embeddable
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FailedToScore implements Serializable {
        @Column(name = "failed_to_score_away")
        private Integer away;

        @Column(name = "failed_to_score_home")
        private Integer home;

        @Column(name = "failed_to_score_total")
        private Integer total;
    }

    @Embeddable
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Fixtures implements Serializable {

        @Embedded
        @AttributeOverride(name = "home", column = @Column(name = "draws_home"))
        @AttributeOverride(name = "away", column = @Column(name = "draws_away"))
        @AttributeOverride(name = "total", column = @Column(name = "draws_total"))
        private HomeAwayTotal draws;

        @Embedded
        @AttributeOverride(name = "home", column = @Column(name = "loses_home"))
        @AttributeOverride(name = "away", column = @Column(name = "loses_away"))
        @AttributeOverride(name = "total", column = @Column(name = "loses_total"))
        private HomeAwayTotal loses;

        @Embedded
        @AttributeOverride(name = "home", column = @Column(name = "played_home"))
        @AttributeOverride(name = "away", column = @Column(name = "played_away"))
        @AttributeOverride(name = "total", column = @Column(name = "played_total"))
        private HomeAwayTotal played;

        @Embedded
        @AttributeOverride(name = "home", column = @Column(name = "wins_home"))
        @AttributeOverride(name = "away", column = @Column(name = "wins_away"))
        @AttributeOverride(name = "total", column = @Column(name = "wins_total"))
        private HomeAwayTotal wins;
    }

    @Embeddable
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HomeAwayTotal implements Serializable {
        private String home;
        private String away;
        private String total;
    }

    @Embeddable
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Goals implements Serializable {

        @Embedded
        @JsonProperty("for")
        private GoalsFor goalsFor;


        @Embedded
        @JsonProperty("against")
        private GoalsAgainst goalsAgainst;

        @Embeddable
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class GoalsFor implements Serializable{
            @Embedded
            @AttributeOverride(name = "home", column = @Column(name = "average_goals_for_home"))
            @AttributeOverride(name = "away", column = @Column(name = "average_goals_for_away"))
            @AttributeOverride(name = "total", column = @Column(name = "average_goals_for_total"))
            private AverageGoals average;

            @ElementCollection
            @CollectionTable(name = "teams_goals_for_per_min", joinColumns = @JoinColumn(name = "statistics_id"))
            private Map<String, GoalMinuteStats> minute;

            @Embedded
            @AttributeOverride(name = "home", column = @Column(name = "total_goals_for_home"))
            @AttributeOverride(name = "away", column = @Column(name = "total_goals_for_away"))
            @AttributeOverride(name = "total", column = @Column(name = "total_goals_for"))
            private HomeAwayTotal total;
        }

        @Embeddable
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class GoalsAgainst implements Serializable {
            @Embedded
            @AttributeOverride(name = "home", column = @Column(name = "average_goals_against_home"))
            @AttributeOverride(name = "away", column = @Column(name = "average_goals_against_away"))
            @AttributeOverride(name = "total", column = @Column(name = "average_goals_against_total"))
            private AverageGoals average;

            @ElementCollection
            @CollectionTable(name = "teams_goals_against_per_min", joinColumns = @JoinColumn(name = "statistics_id"))
            private Map<String, GoalMinuteStats> minute;

            @Embedded
            @AttributeOverride(name = "home", column = @Column(name = "total_goals_against_home"))
            @AttributeOverride(name = "away", column = @Column(name = "total_goals_against_away"))
            @AttributeOverride(name = "total", column = @Column(name = "total_goals_against"))
            private HomeAwayTotal total;
        }


    }

    @Embeddable
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AverageGoals implements Serializable {
        private String home;
        private String away;
        private String total;
    }

    @Embeddable
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GoalMinuteStats implements Serializable {
        @Column(name = "goal_minute_percentage")
        private String percentage;

        @Column(name = "goal_minute_total")
        private Integer total;
    }

    @Embeddable
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LeagueInfo implements Serializable {

        @Transient
        private String country;

        @Transient
        private String flag;

        @JsonProperty("id")
        private Integer leagueId;

        @JsonProperty("logo")
        @Transient
        private String leagueLogo;

        @JsonProperty("name")
        private String leagueName;

        private Integer season;
    }

    @Embeddable
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Lineup implements Serializable {
        private String formation;
        private Integer played;
    }

    @Embeddable
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Penalty implements Serializable {

        @Embedded
        @AttributeOverride(name = "percentage", column = @Column(name = "penalties_missed_percentage"))
        @AttributeOverride(name = "total", column = @Column(name = "penalties_missed_total"))
        private PenaltyStats missed;

        @Embedded
        @AttributeOverride(name = "percentage", column = @Column(name = "penalties_scored_percentage"))
        @AttributeOverride(name = "total", column = @Column(name = "penalties_scored_total"))
        private PenaltyStats scored;

        private Integer total;
    }

    @Embeddable
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PenaltyStats implements Serializable {
        private String percentage;
        private Integer total;
    }

    @Embeddable
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Cards implements Serializable {
        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "minute0To15.percentage", column = @Column(name = "red_0_15_percentage")),
                @AttributeOverride(name = "minute0To15.total", column = @Column(name = "red_0_15_total")),
                @AttributeOverride(name = "minute106To120.percentage", column = @Column(name = "red_106_120_percentage")),
                @AttributeOverride(name = "minute106To120.total", column = @Column(name = "red_106_120_total")),
                @AttributeOverride(name = "minute16To30.percentage", column = @Column(name = "red_16_30_percentage")),
                @AttributeOverride(name = "minute16To30.total", column = @Column(name = "red_16_30_total")),
                @AttributeOverride(name = "minute31To45.percentage", column = @Column(name = "red_31_45_percentage")),
                @AttributeOverride(name = "minute31To45.total", column = @Column(name = "red_31_45_total")),
                @AttributeOverride(name = "minute46To60.percentage", column = @Column(name = "red_46_60_percentage")),
                @AttributeOverride(name = "minute46To60.total", column = @Column(name = "red_46_60_total")),
                @AttributeOverride(name = "minute61To75.percentage", column = @Column(name = "red_61_75_percentage")),
                @AttributeOverride(name = "minute61To75.total", column = @Column(name = "red_61_75_total")),
                @AttributeOverride(name = "minute76To90.percentage", column = @Column(name = "red_76_90_percentage")),
                @AttributeOverride(name = "minute76To90.total", column = @Column(name = "red_76_90_total")),
                @AttributeOverride(name = "minute91To105.percentage", column = @Column(name = "red_91_105_percentage")),
                @AttributeOverride(name = "minute91To105.total", column = @Column(name = "red_91_105_total"))
        })
        private Cards.Card red;

        @Embedded
        @AttributeOverrides({
                @AttributeOverride(name = "minute0To15.percentage", column = @Column(name = "yellow_0_15_percentage")),
                @AttributeOverride(name = "minute0To15.total", column = @Column(name = "yellow_0_15_total")),
                @AttributeOverride(name = "minute106To120.percentage", column = @Column(name = "yellow_106_120_percentage")),
                @AttributeOverride(name = "minute106To120.total", column = @Column(name = "yellow_106_120_total")),
                @AttributeOverride(name = "minute16To30.percentage", column = @Column(name = "yellow_16_30_percentage")),
                @AttributeOverride(name = "minute16To30.total", column = @Column(name = "yellow_16_30_total")),
                @AttributeOverride(name = "minute31To45.percentage", column = @Column(name = "yellow_31_45_percentage")),
                @AttributeOverride(name = "minute31To45.total", column = @Column(name = "yellow_31_45_total")),
                @AttributeOverride(name = "minute46To60.percentage", column = @Column(name = "yellow_46_60_percentage")),
                @AttributeOverride(name = "minute46To60.total", column = @Column(name = "yellow_46_60_total")),
                @AttributeOverride(name = "minute61To75.percentage", column = @Column(name = "yellow_61_75_percentage")),
                @AttributeOverride(name = "minute61To75.total", column = @Column(name = "yellow_61_75_total")),
                @AttributeOverride(name = "minute76To90.percentage", column = @Column(name = "yellow_76_90_percentage")),
                @AttributeOverride(name = "minute76To90.total", column = @Column(name = "yellow_76_90_total")),
                @AttributeOverride(name = "minute91To105.percentage", column = @Column(name = "yellow_91_105_percentage")),
                @AttributeOverride(name = "minute91To105.total", column = @Column(name = "yellow_91_105_total"))
        })
        private Cards.Card yellow;

        @Embeddable
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Card implements Serializable {
            @JsonProperty("0-15")
            @Embedded
            private Cards.CardMinuteStats minute0To15;

            @JsonProperty("106-120")
            @Embedded
            private Cards.CardMinuteStats minute106To120;

            @JsonProperty("16-30")
            @Embedded
            private Cards.CardMinuteStats minute16To30;

            @JsonProperty("31-45")
            @Embedded
            private Cards.CardMinuteStats minute31To45;

            @JsonProperty("46-60")
            @Embedded
            private Cards.CardMinuteStats minute46To60;

            @JsonProperty("61-75")
            @Embedded
            private Cards.CardMinuteStats minute61To75;

            @JsonProperty("76-90")
            @Embedded
            private Cards.CardMinuteStats minute76To90;

            @JsonProperty("91-105")
            @Embedded
            private Cards.CardMinuteStats minute91To105;
        }

        @Embeddable
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class CardMinuteStats implements Serializable {
            private String percentage;
            private Integer total;
        }
    }

}
