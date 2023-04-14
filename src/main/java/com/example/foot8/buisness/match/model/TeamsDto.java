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

public class TeamsDto {

    private TeamDto home;
    private TeamDto away;
}

