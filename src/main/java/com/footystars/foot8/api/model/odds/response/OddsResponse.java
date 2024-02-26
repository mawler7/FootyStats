package com.footystars.foot8.api.model.odds.response;


import com.footystars.foot8.api.model.odds.model.OddsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class OddsResponse {
    private List<OddsDto> response;
}
