package com.footystars.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
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
public class TeamsInfo implements Serializable {

    private List<TeamDto> response;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TeamDto implements Serializable {
        @JsonProperty("team")
        private TeamInfo info;
        private VenueDto venue;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Embeddable
    public static class TeamInfo implements Serializable {
        @JsonProperty("id")
        private Long clubId;
        @JsonProperty("name")
        private String teamName;
        @Transient
        private String code;
        @Transient
        private Integer founded;
        private String logo;
        private String country;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Embeddable
    public static class VenueDto implements Serializable {
        @JsonProperty("name")
        private String venueName;
        private String city;
    }
}


