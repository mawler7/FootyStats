package com.footystars.foot8.api.model.players.model.statistics;

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
public class Penalty implements Serializable {

    private Integer won;
    private Integer commited;
    private Integer scored;
    private Integer missed;
    private Integer saved;
}
