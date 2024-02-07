package com.example.foot8.api.players.model.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
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
