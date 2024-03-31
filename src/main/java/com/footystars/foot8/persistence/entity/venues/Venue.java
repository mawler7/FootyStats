package com.footystars.foot8.persistence.entity.venues;

import com.footystars.foot8.persistence.entity.club.Club;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "venues")
public class Venue {

    @Id
    private Long id;

    private String name;
    private String address;
    private String city;

    private Long capacity;
    private String surface;
    private String image;

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Club> clubs;

}