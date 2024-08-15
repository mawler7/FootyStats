package com.footystars.foot8.business.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamDto implements Serializable {
   private Long clubId;

   private String name;
   private String code;
   private String logo;

   private String venue;
   private String address;
   private String city;

   private Long capacity;
   private String surface;
   private String image;
   private Integer founded;
   private boolean national;

   private String country;

   private TeamStatsDto statistics;
   private CoachDto coach;

   @OneToMany(fetch = FetchType.EAGER)
   private List<PlayerDto> players;

}