package com.footystars.foot8.persistence.entity.club;

import com.footystars.foot8.persistence.entity.countries.Country;
import com.footystars.foot8.persistence.entity.venues.Venue;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "clubs")
public class Club {

    @Id
    private Long id;

    private String name;
    private String code;
    private String logo;
    private Integer founded;
    private boolean national;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;


}