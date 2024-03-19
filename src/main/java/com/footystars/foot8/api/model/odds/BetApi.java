package com.footystars.foot8.api.model.odds;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetApi {
    private Long id;
    private String name;
    private List<OddValue> values;
}
