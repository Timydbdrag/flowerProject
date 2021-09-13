package com.greenstreet.warehouse.web.controller;

import com.greenstreet.warehouse.entity.Country;
import com.greenstreet.warehouse.services.CountryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/country")
@Tag(name = "Country", description = "Country list management")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<Country>> getAll() {
        return ResponseEntity.ok().body(countryService.getAll());
    }

    @PostMapping
    public ResponseEntity<Country> createCountry(@Valid @RequestBody Country country) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/country").toUriString());
        return ResponseEntity.created(uri).body(countryService.create(country));
    }

    @PutMapping
    public ResponseEntity<Country> updateCountry(@Valid @RequestBody Country country) {
        return ResponseEntity.ok().body(countryService.update(country));
    }
}
