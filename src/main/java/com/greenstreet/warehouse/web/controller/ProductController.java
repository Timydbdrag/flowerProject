package com.greenstreet.warehouse.web.controller;

import com.greenstreet.warehouse.entity.Product;
import com.greenstreet.warehouse.model.ProductDTO;
import com.greenstreet.warehouse.services.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
@Tag(name = "Product", description = "Product management")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<Product>> getProducts(Pageable pageable) {
        return ResponseEntity.ok().body(productService.getProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok().body(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/product").toUriString());
        return ResponseEntity.created(uri).body(productService.create(productDTO));
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok().body(productService.update(productDTO));
    }
}
