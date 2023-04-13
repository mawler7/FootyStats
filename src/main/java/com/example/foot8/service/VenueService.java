package com.example.foot8.service;

import com.example.foot8.buisness.venue.response.Response;
import com.example.foot8.persistence.entities.VenueEntity;
import com.example.foot8.persistence.repository.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VenueService {

    private final VenueRepository venueRepository;

    public void saveVenue(Response response) {

        Optional<VenueEntity> existingVenue = venueRepository.findById(response.getId());

        if(existingVenue.isPresent()) {
            VenueEntity venue = existingVenue.get();
            if(venue.getCapacity() == null) {
                  venue.setCapacity(response.getCapacity());
            }
            if(venue.getCity() == null) {
                venue.setCity(response.getCity());
            }
            if(venue.getSurface() == null) {
                venue.setSurface(response.getSurface());
            }
            if(venue.getAddress() == null) {
                venue.setAddress(response.getAddress());
            }
            venueRepository.save(venue);
        } else {
            venueRepository.save(
                    VenueEntity.builder()
                            .id(response.getId())
                            .image(response.getImage())
                            .surface(response.getSurface())
                            .country(response.getCountry())
                            .address(response.getAddress())
                            .capacity(response.getCapacity())
                            .name(response.getName())
                            .city(response.getCity())
                            .build());
        }
    }
}
