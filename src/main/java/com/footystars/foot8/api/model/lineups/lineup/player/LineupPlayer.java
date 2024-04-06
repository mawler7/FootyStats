package com.footystars.foot8.api.model.lineups.lineup.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LineupPlayer implements Serializable {

    private Long id;
    private String name;
    private Integer number;
    private String pos;
    private String grid;

}
