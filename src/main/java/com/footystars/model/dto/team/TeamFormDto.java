package com.footystars.model.dto.team;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.model.dto.fixture.ClubMatchDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Builder
public class TeamFormDto implements Serializable {
    private List<String> formResults;
    private ClubMatchDto nextMatch;
}
