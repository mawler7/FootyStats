package com.footystars.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class H2HDto {

    private List<MatchDto> lastHomeMatches;
    private List<MatchDto> lastAwayMatches;
    private List<MatchDto> headToHeadMatches;
}
