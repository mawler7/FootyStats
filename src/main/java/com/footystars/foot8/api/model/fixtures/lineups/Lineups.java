package com.footystars.foot8.api.model.fixtures.lineups;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.fixtures.lineups.lineup.Lineupz;
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
public class Lineups implements Serializable {

    @JsonProperty("response")
    private List<Lineupz> lineupList;

}
