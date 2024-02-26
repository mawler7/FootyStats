package com.footystars.foot8.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carrier {

    private String teamName;
    private Long teamNumber;
    private Timestamp startDate;
    private Timestamp endDate;


}
