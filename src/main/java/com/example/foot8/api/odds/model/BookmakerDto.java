package com.example.foot8.api.odds.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookmakerDto {
    private Long id;
    private String name;
    @JsonProperty("bets")
    private List<BetDto> bets;
}
