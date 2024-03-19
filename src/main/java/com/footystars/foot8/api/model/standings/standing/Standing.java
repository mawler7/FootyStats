package com.footystars.foot8.api.model.standings.standing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.standings.standing.stat.StandingStat;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
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

    @JsonProperty("group")
    private String groupInfo;
    private String form;
    private String status;
    private String description;
    private Integer rank;
    private Integer points;
    private Integer goalsDiff;
    @AttributeOverride(name = "goals.forGoals", column = @Column(name = "goals_for"))
    @AttributeOverride(name = "goals.against", column = @Column(name = "goals_against"))
    private StandingStat stats;
    @AttributeOverride(name = "goals.forGoals", column = @Column(name = "goals_for_home"))
    @AttributeOverride(name = "goals.against", column = @Column(name = "goals_against_home"))
    private StandingStat homeStats;
    @AttributeOverride(name = "goals.forGoals", column = @Column(name = "goals_for_away"))
    @AttributeOverride(name = "goals.against", column = @Column(name = "goals_against_away"))
    private StandingStat awayStats;

    private Date update;

}
