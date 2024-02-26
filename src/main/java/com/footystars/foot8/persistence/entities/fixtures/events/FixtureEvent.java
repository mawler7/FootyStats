package com.footystars.foot8.persistence.entities.fixtures.events;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class FixtureEvent implements Serializable {
    private Integer elapsed;
    private Integer extra;
    private Long teamId;
    private String teamName;
    private String teamLogo;
    private Long playerId;
    private String playerName;
    private Long assistId;
    private String assistName;
    private String type;
    private String detail;
    private String comments;
}