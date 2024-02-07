package com.example.foot8.api.players.model.statistics;

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
public class Goals implements Serializable {

    private Integer total;
    private Integer conceded;
    private Integer assists;
    private Integer saves;
}