package com.footystars.foot8.api.model.leagues;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.footystars.foot8.api.model.leagues.league.LeagueApi;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Leagues {

    private List<LeagueApi> response;
}
