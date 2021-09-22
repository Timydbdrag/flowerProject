package com.greenstreet.warehouse.web.controller;

import com.greenstreet.warehouse.model.request.OrderDTO;
import com.greenstreet.warehouse.model.request.OrderDTOAdmin;
import com.greenstreet.warehouse.model.response.ResponseOrderDTO;
import com.greenstreet.warehouse.services.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
@Tag(name = "Order", description = "Order management")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Page<ResponseOrderDTO>> getAllOrders(Pageable pageable) {
        return ResponseEntity.ok().body(orderService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseOrderDTO> getOrder(@PathVariable(name = "id")String id) {
        return ResponseEntity.ok().body(orderService.getOrder(UUID.fromString(id)));
    }

    @PostMapping
    public ResponseEntity<ResponseOrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/order").toUriString());
        return ResponseEntity.created(uri).body(orderService.create(orderDTO));
    }

    @PutMapping
    public ResponseEntity<ResponseOrderDTO> updateOrder(@Valid @RequestBody OrderDTOAdmin order) {
        return ResponseEntity.ok().body(orderService.update(order));
    }

    @GetMapping("/user")
    public ResponseEntity<Set<ResponseOrderDTO>> getUserOrders() {
        return ResponseEntity.ok().body(orderService.getUserOrders());
    }

}
