package com.greenstreet.warehouse.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenstreet.warehouse.entity.Country;
import com.greenstreet.warehouse.services.CountryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CountryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    WebApplicationContext webApplicationContext;

    @MockBean
    CountryService countryService;

    @Test
    @WithMockUser(value = "tester", roles = "MANAGER")
    void getAll() throws Exception {
        String uri = "/api/country";

        List<Country> testCountries= List.of(getTestCountry());
        given(countryService.getAll()).willReturn(testCountries);

        mockMvc.perform(get(uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[*].id").value(testCountries.get(0).getId()))
                .andExpect(jsonPath("$.[*].name").value(testCountries.get(0).getName()));
    }

    @Test
    @WithMockUser(value = "tester", roles = "MANAGER")
    void createCountry() throws Exception {
        String uri = "/api/country";

        Country testCountry = getTestCountry();
        given(countryService.create(testCountry)).willReturn(testCountry);

        String county = objectMapper.writeValueAsString(testCountry);

        mockMvc.perform(post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(county))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testCountry.getId()))
                .andExpect(jsonPath("$.name").value(testCountry.getName()));
    }

    @Test
    @WithMockUser(value = "tester", roles = "MANAGER")
    void updateCountry() throws Exception {
        String uri = "/api/country";

        Country testCountry = getTestCountry();
        given(countryService.update(testCountry)).willReturn(testCountry);

        String county = objectMapper.writeValueAsString(testCountry);

        mockMvc.perform(put(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(county))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testCountry.getId()))
                .andExpect(jsonPath("$.name").value(testCountry.getName()));
    }

    private Country getTestCountry() {
        return new Country(1, "Kenya");
    }
}