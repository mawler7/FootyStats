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
public class TeamHTDrawsDto {
    private String logo;
    private String team;
    private int draws;
    private int totalMatches;
}