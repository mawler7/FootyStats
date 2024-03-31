package com.footystars.foot8.api.model.players.info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.footystars.foot8.api.model.players.info.birth.Birth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerInfo implements Serializable {
    @JsonProperty("id")
    private Long playerId;
    private String name;
    private String firstname;
    private String lastname;
    private String nationality;
    private Integer age;
    private String height;
    private String weight;
    private String photo;
    private Boolean injured;
    private Birth birth;
}