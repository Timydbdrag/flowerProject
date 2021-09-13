package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.Country;

import java.util.List;

public interface CountryService {
    Country create(Country country);
    Country update(Country country);
    List<Country> getAll();
}
