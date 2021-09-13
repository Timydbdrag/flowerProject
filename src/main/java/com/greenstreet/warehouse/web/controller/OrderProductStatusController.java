package com.greenstreet.warehouse.web.controller;

import com.greenstreet.warehouse.entity.OrderProductStatus;
import com.greenstreet.warehouse.services.OrderProductStatusService;
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
@RequestMapping("/api/order/product/status")
@Tag(name = "Order-product-status", description = "Managing product status in orders")
public class OrderProductStatusController {

    private final OrderProductStatusService statusService;

    @GetMapping
    public ResponseEntity<List<OrderProductStatus>> getAll() {
        return ResponseEntity.ok().body(statusService.getAll());
    }

    @PostMapping
    public ResponseEntity<OrderProductStatus> createStatus(@Valid @RequestBody OrderProductStatus status) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/order/product/status").toUriString());
        return ResponseEntity.created(uri).body(statusService.create(status));
    }

    @PutMapping
    public ResponseEntity<OrderProductStatus> updateStatus(@Valid @RequestBody OrderProductStatus status) {
        return ResponseEntity.ok().body(statusService.update(status));
    }

}
