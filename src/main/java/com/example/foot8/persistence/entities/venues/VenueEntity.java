package com.example.foot8.persistence.entities.venues;

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
@Table(name = "venues")
public class VenueEntity {
    @Id
    private Long id;
    private String name;
    private String city;
    private String address;
    private String country;
    private Long capacity;
    private String surface;
    private String image;

}