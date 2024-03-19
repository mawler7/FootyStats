package com.footystars.foot8.persistence.entities.players.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.footystars.foot8.api.model.players.info.birth.Birth;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Embeddable
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

}