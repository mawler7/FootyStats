package com.footystars.model.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.JoinColumn;
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
public class Coaches implements Serializable {

    @JsonProperty("response")
    private List<CoachDto> coachesDto;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CoachDto implements Serializable {
        private Long id;
        private String name;
        private String firstname;
        private String lastname;
        @Embedded
        private Birth birth;
        @Embedded
        private Team team;

        @ElementCollection
        @CollectionTable(name = "coach_career", joinColumns = @JoinColumn(name = "coach_id"))
        private List<Career> career;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Embeddable
        public static class Birth implements Serializable {
            @JsonProperty("date")
            private String birthDate;
            @JsonProperty("place")
            private String birthPlace;
            @JsonProperty("country")
            private String birthCountry;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @Embeddable
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Career implements Serializable {
            @Embedded
            private Team team;
            @JsonProperty("start")
            private String startDate;
            @JsonProperty("end")
            private String endDate;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @Embeddable
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Team implements Serializable {
            @JsonProperty("id")
            private Long teamId;
            @JsonProperty("name")
            private String teamName;
            @JsonProperty("logo")
            private String teamLogo;
        }
    }

}
