package com.greenstreet.warehouse.services.impl;

import com.greenstreet.warehouse.entity.Country;
import com.greenstreet.warehouse.exception.ApiRequestException;
import com.greenstreet.warehouse.repository.CountryRepository;
import com.greenstreet.warehouse.services.CountryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.greenstreet.warehouse.exception.ExceptionConstant.COUNTRY_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public Country create(Country country) {
        log.info("A new country has been added to the database, name: {} ", country.getName());
        country.setId(null);
        return countryRepository.save(country);
    }

    @Override
    public Country update(Country country) {
        Country countryDB = countryRepository.findById(country.getId())
                .orElseThrow(() -> new ApiRequestException(COUNTRY_NOT_FOUND));

        log.info("Country name {} changed to {}", countryDB.getName(), country.getName());
        countryDB.setName(country.getName());

        return countryRepository.save(country);
    }

    @Override
    public List<Country> getAll() {
        return countryRepository.findAll();
    }
}
