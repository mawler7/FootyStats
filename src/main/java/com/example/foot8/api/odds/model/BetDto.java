package com.example.foot8.api.odds.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BetDto {
    private Long id;
    private String name;
    private List<OddValueDto> values;
}
