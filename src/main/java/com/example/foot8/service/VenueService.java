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
        VenueEntity existingVenue = venueRepository.findById(response.getId())
                .orElse(new VenueEntity());

        if (response.getCapacity() != null) {
            existingVenue.setCapacity(response.getCapacity());
        }
        if (response.getCity() != null) {
            existingVenue.setCity(response.getCity());
        }
        if (response.getSurface() != null) {
            existingVenue.setSurface(response.getSurface());
        }
        if (response.getAddress() != null) {
            existingVenue.setAddress(response.getAddress());
        }
        existingVenue.setId(response.getId());
        existingVenue.setImage(response.getImage());
        existingVenue.setCountry(response.getCountry());
        existingVenue.setName(response.getName());

        venueRepository.save(existingVenue);
    }

    public void save(VenueEntity venueEntity){
        venueRepository.save(venueEntity);
    }

    public Optional<VenueEntity> findById(Long id){
        return venueRepository.findById(id);
    }

}
