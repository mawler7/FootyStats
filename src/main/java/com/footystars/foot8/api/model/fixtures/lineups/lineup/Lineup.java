package com.footystars.foot8.api.model.fixtures.lineups.lineup;

import com.footystars.foot8.api.model.fixtures.lineups.lineup.coach.Coach;
import com.footystars.foot8.api.model.fixtures.lineups.lineup.player.Player;
import com.footystars.foot8.api.model.fixtures.lineups.lineup.team.Team;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Lineup implements Serializable {

    private Team team;
    private Coach coach;
    private String formation;
    private List<Player> startXI;
    private List<Player> substitutes;

}
