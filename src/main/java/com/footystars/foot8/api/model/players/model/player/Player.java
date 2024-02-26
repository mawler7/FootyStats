package com.footystars.foot8.api.model.players.model.player;


import com.footystars.foot8.api.model.players.model.player.birth.Birth;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Player implements Serializable {

    private Long id;
    private String name;
    private String firstname;
    private String lastname;
    private Integer age;
    private Birth birth;
    private String nationality;
    private String height;
    private String weight;
    private Boolean injured;
    private String photo;

}
