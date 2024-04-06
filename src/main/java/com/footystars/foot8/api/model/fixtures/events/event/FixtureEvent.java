package com.footystars.foot8.api.model.fixtures.events.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.fixtures.events.assist.Assist;
import com.footystars.foot8.api.model.fixtures.events.player.Player;
import com.footystars.foot8.api.model.fixtures.events.team.Team;
import com.footystars.foot8.api.model.fixtures.events.time.Time;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Embeddable
public class FixtureEvent implements Serializable {

    private String type;
    private String detail;
    private String comments;

    @Embedded
    private Time time;

    @Embedded
    private Team team;

    @Embedded
    private Player player;

    @Embedded
    private Assist assist;

}