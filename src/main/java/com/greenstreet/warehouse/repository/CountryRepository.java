package com.greenstreet.warehouse.repository;

import com.greenstreet.warehouse.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}
