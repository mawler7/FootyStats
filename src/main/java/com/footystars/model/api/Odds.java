package com.footystars.model.api;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Odds implements Serializable {

    private List<OddsResponse> response;
    private Paging paging;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Paging implements Serializable {
        private Integer total;
        private Integer current;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class OddsResponse implements Serializable {
        private OddFixture fixture;
        private ZonedDateTime update;
        private List<BookmakerDto> bookmakers;

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class OddFixture implements Serializable {
            @JsonProperty("id")
            @Transient
            private Long fixtureId;
        }

        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        @Getter
        @Setter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class BookmakerDto implements Serializable {
            @JsonProperty("id")
            @Transient
            private Long bookmakerId;
            private String name;
            private List<BetDto> bets;

            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            @Getter
            @Setter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class BetDto implements Serializable {
                @JsonProperty("id")
                @Transient
                private Long betId;
                private String name;
                private List<BetValues> values;

                @NoArgsConstructor
                @AllArgsConstructor
                @Builder
                @Getter
                @Setter
                @JsonIgnoreProperties(ignoreUnknown = true)
                public static class BetValues implements Serializable {
                    private String value;
                    private double odd;
                }

            }

        }

    }

    }

