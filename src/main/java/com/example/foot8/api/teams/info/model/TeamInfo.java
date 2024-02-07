package com.example.foot8.api.teams.info.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class TeamInfo {
    private Long id;
    private String name;
    private String code;
    private String country;
    private Integer founded;
    private boolean national;
    private String logo;
}
