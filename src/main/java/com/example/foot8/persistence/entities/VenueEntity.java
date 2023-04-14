package com.example.foot8.persistence.entities;

import com.example.foot8.buisness.match.model.VenueMatchDto;
import com.example.foot8.buisness.venue.model.VenueDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

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

    public VenueEntity(VenueMatchDto venueDto) {
        this.id = venueDto.getId();
        this.name = venueDto.getName();
        this.city = venueDto.getCity();

    }

    public VenueEntity(VenueDto venueDto) {
        this.id = venueDto.getId();
        this.name = venueDto.getName();
        this.city = venueDto.getCity();
        this.address = venueDto.getAddress();
        this.country = venueDto.getCountry();
        this.capacity = venueDto.getCapacity();
        this.surface = venueDto.getSurface();
    }

    public boolean isEqual(VenueDto venueDto) {
        return Objects.equals(this.capacity, venueDto.getCapacity()) &&
                Objects.equals(this.city, venueDto.getCity()) &&
                Objects.equals(this.surface, venueDto.getSurface()) &&
                Objects.equals(this.address, venueDto.getAddress()) &&
                Objects.equals(this.image, venueDto.getImage()) &&
                Objects.equals(this.country, venueDto.getCountry()) &&
                Objects.equals(this.name, venueDto.getName());
    }

}