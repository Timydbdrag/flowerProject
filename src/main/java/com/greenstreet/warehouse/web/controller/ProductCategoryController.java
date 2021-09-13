package com.greenstreet.warehouse.web.controller;

import com.greenstreet.warehouse.entity.ProductCategory;
import com.greenstreet.warehouse.services.ProductCategoryService;
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
@RequestMapping("/api/category")
@Tag(name = "Product-category", description = "Category management for product")
public class ProductCategoryController {

    private final ProductCategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getCategories() {
        return ResponseEntity.ok().body(categoryService.getAll());
    }

    @PostMapping
    public ResponseEntity<ProductCategory> createCategory(@Valid @RequestBody ProductCategory category) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/category").toUriString());
        return ResponseEntity.created(uri).body(categoryService.create(category));
    }

    @PutMapping
    public ResponseEntity<ProductCategory> updateCategory(@Valid @RequestBody ProductCategory category) {
        return ResponseEntity.ok().body(categoryService.update(category));
    }

}
