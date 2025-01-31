package com.footystars.model.dto.player;
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
    private String clubLogo;
    private String leagueName;
    private String leagueLogo;
    private String form;
    private int appearances;
    private int goals;
    private int assists;
    private int yellowCards;
    private int redCards;
}