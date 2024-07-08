package com.footystars.foot8.api.model.sidelined;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
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
@Embeddable
public class SidelinedApi implements Serializable {

    private String type;
    @JsonProperty("start")
    private String started;
    @JsonProperty("end")
    private String ended;

}
