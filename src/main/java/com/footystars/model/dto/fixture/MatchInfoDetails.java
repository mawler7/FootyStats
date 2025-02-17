package com.footystars.model.dto.fixture;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.model.dto.team.TeamFormDto;
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
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class MatchInfoDetails implements Serializable {
    private MatchInfo match;
    private ClubMatchDto homeTeamForm;
    private ClubMatchDto awayTeamForm;
}
