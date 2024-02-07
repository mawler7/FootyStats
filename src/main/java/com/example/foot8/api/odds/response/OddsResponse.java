package com.example.foot8.api.odds.response;

import com.example.foot8.api.fixture.response.Paging;
import com.example.foot8.api.fixture.response.Parameters;
import com.example.foot8.api.odds.model.OddsDto;
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
    private String get;
    private Parameters parameters;
    private List<String> errors;
    private Integer results;
    private Paging paging;
    private List<OddsDto> response;
}
