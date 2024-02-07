package com.example.foot8.api.players.response;

import com.example.foot8.api.fixture.response.Paging;
import com.example.foot8.api.players.model.PlayerResponseData;
import com.example.foot8.api.venue.response.Parameters;
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
public class PlayerResponse {
    private String get;
    private Parameters parameters;
    private List<String> errors;
    private Integer results;
    private Paging paging;
    private List<PlayerResponseData> response;
}
