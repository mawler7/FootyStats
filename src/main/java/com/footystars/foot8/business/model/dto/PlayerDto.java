package com.footystars.foot8.business.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.players.info.birth.Birth;
import com.footystars.foot8.api.model.players.statistics.PlayerStats;
import com.footystars.foot8.api.model.sidelined.SidelinedApi;
import com.footystars.foot8.api.model.trophies.Trophies;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerDto implements Serializable {
    private Long id;
    private String name;
    private String firstname;
    private String lastname;
    private String nationality;
    private Integer age;
    private String height;
    private String weight;
    private String photo;
    private boolean injured;
    private Birth birth;
    private List<PlayerStats> statistics;
    private String zodiac;
    private List<SidelinedApi> sidelined;
    private List<Trophies> trophies;

}