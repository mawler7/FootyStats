package com.example.foot8.buisness.match.model;

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
public class LeagueDto {
    private Long id;
    private String name;
    private String country;
    private String logo;
    private String flag;
    private Long season;
    private String round;
}