package com.greenstreet.warehouse.web.controller;

import com.greenstreet.warehouse.entity.OrderStatus;
import com.greenstreet.warehouse.services.OrderStatusService;
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
@RequestMapping("/api/order/status")
@Tag(name = "Order-status", description = "Status management for orders")
public class OrderStatusController {

    private final OrderStatusService orderStatusService;

    @GetMapping
    public ResponseEntity<List<OrderStatus>> getAll() {
        return ResponseEntity.ok().body(orderStatusService.getAll());
    }

    @PostMapping
    public ResponseEntity<OrderStatus> createOrderStatus(@Valid @RequestBody OrderStatus orderStatus) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/order/status").toUriString());
        return ResponseEntity.created(uri).body(orderStatusService.create(orderStatus));
    }

    @PutMapping
    public ResponseEntity<OrderStatus> updateOrderStatus(@Valid @RequestBody OrderStatus orderStatus) {
        return ResponseEntity.ok().body(orderStatusService.update(orderStatus));
    }

}
