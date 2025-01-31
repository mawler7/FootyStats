package com.footystars.model.dto.fixture;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MatchScoreDto {
    private Integer home;
    private Integer away;
    private Integer halfTimeHome;
    private Integer halfTimeAway;
    private Integer fullTimeHome;
    private Integer fullTimeAway;
    private Integer extraTimeHome;
    private Integer extraTimeAway;
    private Integer penaltiesHome;
    private Integer penaltiesAway;
}
