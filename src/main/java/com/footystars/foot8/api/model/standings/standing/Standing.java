package com.footystars.foot8.api.model.standings.standing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.standings.standing.stat.AllStat;
import com.footystars.foot8.api.model.standings.standing.stat.AwayStat;
import com.footystars.foot8.api.model.standings.standing.stat.HomeStat;
import com.footystars.foot8.api.model.standings.standing.team.StandingTeam;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Standing implements Serializable {

    @Embedded
    private StandingTeam team;
    private Integer rank;
    private Integer points;
    private Integer goalsDiff;

    @JsonProperty("group")
    private String groupInfo;
    private String form;
    private String status;
    private String description;

    @AttributeOverride(name ="played", column = @Column(name = "played_total"))
    @AttributeOverride(name ="win", column = @Column(name = "win_total"))
    @AttributeOverride(name ="draw", column = @Column(name = "draw_total"))
    @AttributeOverride(name ="lose", column = @Column(name = "lose_total"))
    @AttributeOverride(name ="goal.goalsFor", column = @Column(name = "goals_for_total"))
    @AttributeOverride(name ="goal.goalsAgainst", column = @Column(name = "goals_against_total"))
    @Embedded
    @JsonProperty("all")
    private AllStat allStats;

    @AttributeOverride(name ="played",  column = @Column(name = "played_home"))
    @AttributeOverride(name ="win", column = @Column(name = "win_home"))
    @AttributeOverride(name ="draw", column = @Column(name = "draw_home"))
    @AttributeOverride(name ="lose", column = @Column(name = "lose_home"))
    @AttributeOverride(name ="homeGoals.goalsFor", column = @Column(name = "goals_for_home"))
    @AttributeOverride(name ="homeGoals.goalsAgainst", column = @Column(name = "goals_against_home"))
    @Embedded
    @JsonProperty("home")
    private HomeStat homeStats;

    @AttributeOverride(name ="played", column = @Column(name = "played_away"))
    @AttributeOverride(name ="win", column = @Column(name = "win_away"))
    @AttributeOverride(name ="draw", column = @Column(name = "draw_away"))
    @AttributeOverride(name ="lose", column = @Column(name = "lose_away"))
    @AttributeOverride(name ="awayGoals.goalsFor", column = @Column(name = "goals_for_away"))
    @AttributeOverride(name ="awayGoals.goalsAgainst", column = @Column(name = "goals_against_away"))
    @Embedded
    @JsonProperty("away")
    private AwayStat awayStats;

    private Date update;

}
