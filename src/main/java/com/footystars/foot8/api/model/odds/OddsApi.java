package com.footystars.foot8.api.model.odds;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.odds.odd.Odd;
import com.footystars.foot8.api.model.players.paging.Paging;
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
public class OddsApi implements Serializable {
    private List<Odd> response;
    private Paging paging;
}
