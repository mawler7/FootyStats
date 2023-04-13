package com.example.foot8.persistence.entities;

import com.example.foot8.buisness.match.model.TeamDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "teams")
public class TeamEntity {

    @Id
    private Long id;
    private String name;
    private String logo;

    public TeamEntity(TeamDto teamDto) {
        this.id = teamDto.getId();
        this.name = teamDto.getName();
        this.logo = teamDto.getLogo();
    }

}
