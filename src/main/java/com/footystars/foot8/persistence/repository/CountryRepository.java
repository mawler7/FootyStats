package com.footystars.foot8.persistence.repository;


import com.footystars.foot8.persistence.entity.countries.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, String> {


    Optional<Country> findByName(String name);
}