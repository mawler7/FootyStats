package com.example.foot8.api.players.model.statistics;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class League implements Serializable {

    private Long id;
    private String name;
    private String country;
    private String logo;
    private String flag;
    private Long season;

}
