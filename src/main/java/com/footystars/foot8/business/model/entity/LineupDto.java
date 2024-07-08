package com.footystars.foot8.business.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.business.model.dto.PlayerDto;
import com.footystars.foot8.business.model.dto.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineupDto implements Serializable {
    private String formation;
    private TeamDto team;
    private Set<PlayerDto> startXI;
    private Set<PlayerDto> substitutes;
}