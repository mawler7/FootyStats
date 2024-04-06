package com.footystars.foot8.api.model.predictions.response.teams.league;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RedCards  implements Serializable {

    @JsonProperty("0-15")
    private MinuteStats from0To15;
    @JsonProperty("16_30")
    private MinuteStats from16To30;
    @JsonProperty("31_45")
    private MinuteStats from31To45;
    @JsonProperty("46_60")
    private MinuteStats from46To60;
    @JsonProperty("61_75")
    private MinuteStats from61To75;
    @JsonProperty("76_90")
    private MinuteStats from76To90;
    @JsonProperty("91_105")
    private MinuteStats from91To105;
    @JsonProperty("106_120")
    private MinuteStats from106To120;
}

