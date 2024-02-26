package com.footystars.foot8.persistence.repository;


import com.footystars.foot8.persistence.entities.countries.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, String> {

    @Query("select c.code from Country c where c.code =?1 order by c.code desc limit 1")
    Optional<Country> findByCode(String code);

    @Query("select c.name from Country c where c.name =?1 order by c.name desc limit 1")
    Optional<Country> findByName(String name);
}