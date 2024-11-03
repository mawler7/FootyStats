package com.footystars.model.dto;
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
public class PlayerCareerDto {
    private int season;
    private String clubName;
    private String leagueName;
    private String form;
    private int appearances;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;
}