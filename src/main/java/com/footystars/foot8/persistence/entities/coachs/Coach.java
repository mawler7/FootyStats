package com.footystars.foot8.persistence.entities.coachs;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "coachs")
public class Coach {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String nationality;
    private String height;
    private Long teamId;
    private String teamName;
    private String teamLogo;

}
