package com.footystars.model.dto.fixture;

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

    private List<ClubMatchDto> lastHomeMatches;
    private List<ClubMatchDto> lastAwayMatches;
    private List<ClubMatchDto> headToHeadMatches;
}
