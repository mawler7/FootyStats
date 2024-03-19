package com.footystars.foot8.api.model.fixtures.events.time;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class Time implements Serializable {

    private int elapsed;
    private String extra;

}
