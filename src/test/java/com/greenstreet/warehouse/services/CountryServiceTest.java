package com.greenstreet.warehouse.services;

import com.greenstreet.warehouse.entity.Country;
import com.greenstreet.warehouse.repository.CountryRepository;
import com.greenstreet.warehouse.services.impl.CountryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {

    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    CountryServiceImpl countryService;

    @Test
    void create() {
        Country expect = getCountry();
        given(countryRepository.save(any())).willReturn(expect);

        assertThat(countryService.create(new Country())).isEqualTo(expect);
    }

    @Test
    void update() {
        Country country = getCountry();
        given(countryRepository.findById(country.getId())).willReturn(Optional.of(country));
        given(countryRepository.save(any())).willReturn(country);

        assertThat(countryService.update(country)).isEqualTo(country);
    }

    @Test
    void getAll() {
        List<Country> countries = List.of(getCountry());
        given(countryRepository.findAll()).willReturn(countries);

        assertThat(countryService.getAll()).isEqualTo(countries);
    }

    private Country getCountry() {
        return new Country(1, "Kenya");
    }
}