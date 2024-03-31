package com.footystars.foot8.persistence.entity.players.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.players.info.birth.Birth;
import com.footystars.foot8.persistence.entity.players.statistics.PlayerStatsDto;
import com.footystars.foot8.persistence.entity.teams.team.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerDto implements Serializable {
    private Long id;
    private String name;
    private String firstname;
    private String lastname;
    private String nationality;
    private Integer age;
    private String height;
    private String weight;
    private String photo;
    private boolean injured;
    private Birth birth;
    private List<PlayerStatsDto> statistics;
    private TeamDto team;

}