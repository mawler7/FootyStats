package com.footystars.foot8.api.model.players.statistics.dribbles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dribbles implements Serializable {

    private Integer attempts;
    private Integer success;
    private Integer past;

}
