package com.footystars.model.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamCorrectScoresDto {
    private String logo;
    private String team;
    private String result;
    private int resultCount;
    private int totalMatches;
}