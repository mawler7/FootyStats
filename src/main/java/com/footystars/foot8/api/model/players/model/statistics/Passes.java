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
public class Passes implements Serializable {

    private Integer total;
    private Integer key;
    private Integer accuracy;
}
