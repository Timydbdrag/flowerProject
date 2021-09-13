package com.greenstreet.warehouse.web.controller;

import com.greenstreet.warehouse.entity.Color;
import com.greenstreet.warehouse.model.request.ColorDTO;
import com.greenstreet.warehouse.services.ColorService;
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
@RequestMapping("/api/color")
@Tag(name = "Color", description = "Color list management")
public class ColorController {

    private final ColorService colorService;

    @GetMapping
    public ResponseEntity<List<Color>> getAll() {
        return ResponseEntity.ok().body(colorService.getAll());
    }

    @PostMapping
    public ResponseEntity<Color> createColor(@Valid @RequestBody ColorDTO colorDTO) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/color").toUriString());
        return ResponseEntity.created(uri).body(colorService.create(colorDTO));
    }

    @PutMapping
    public ResponseEntity<Color> updateColor(@Valid @RequestBody ColorDTO color) {
        return ResponseEntity.ok().body(colorService.update(color));
    }

}
