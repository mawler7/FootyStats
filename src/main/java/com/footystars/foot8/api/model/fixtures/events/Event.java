package com.footystars.foot8.api.model.fixtures.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.fixtures.events.player.Player;
import com.footystars.foot8.api.model.fixtures.events.team.Team;
import com.footystars.foot8.api.model.fixtures.events.time.Time;
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
public class Event implements Serializable {

    private Time time;
    private Team team;
    private Player player;
    private Player assist;
    private String type;
    private String detail;
    private String comments;

}