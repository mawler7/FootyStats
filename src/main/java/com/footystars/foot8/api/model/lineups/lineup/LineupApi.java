package com.footystars.foot8.api.model.lineups.lineup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.lineups.lineup.startxi.StartXI;
import com.footystars.foot8.api.model.lineups.lineup.substitutes.Substitutes;
import com.footystars.foot8.buisness.model.dto.ClubDto;
import com.footystars.foot8.buisness.model.dto.CoachDto;
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
public class LineupApi implements Serializable {
    @JsonProperty("team")
    private ClubDto club;
    private CoachDto coach;
    private List<StartXI> startXI;
    private List<Substitutes> substitutes;
    private String formation;
}
