package com.footystars.foot8.persistence.entities.teams.standings;

import com.footystars.foot8.api.model.standings.standing.stat.goals.Goals;
import com.footystars.foot8.persistence.entities.leagues.seaon.LeagueSeason;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Standing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rank;
    private Integer points;
    private Integer goalsDiff;

    private String groupInfo;
    private String form;
    private String status;

    private String description;

    private Integer totalPlayed;
    private Integer totalWin;
    private Integer totalDraw;
    private Integer totalLose;

    @AttributeOverride(name = "forGoals", column = @Column(name = "total_goals_for"))
    @AttributeOverride(name = "against", column = @Column(name = "total_goals_against" ))
    private Goals totalGoals;

    private Integer homeTeamPlayed;
    private Integer homeTeamWin;
    private Integer homeTeamDraw;
    private Integer homeTeamLose;
    @AttributeOverride(name = "forGoals", column = @Column(name = "home_goals_for"))
    @AttributeOverride(name = "against", column = @Column(name = "home_goals_against" ))
    private Goals homeTeamGoals;

    private Integer awayTeamPlayed;
    private Integer awayTeamWin;
    private Integer awayTeamDraw;
    private Integer awayTeamLose;
    @AttributeOverride(name = "forGoals", column = @Column(name = "away_goals_for"))
    @AttributeOverride(name = "against", column = @Column(name = "away_goals_against"))
    private Goals awayTeamGoals;

    private Date update;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private LeagueSeason season;


}
