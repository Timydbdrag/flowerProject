package com.greenstreet.warehouse.web.controller;

import com.greenstreet.warehouse.entity.DeliverySchedule;
import com.greenstreet.warehouse.services.DeliveryScheduleService;
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
@RequestMapping("/api/schedule")
@Tag(name = "Delivery-schedule", description = "Schedule management")
public class DeliveryScheduleController {

    private final DeliveryScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<Page<DeliverySchedule>> getAll(Pageable pageable) {
        return ResponseEntity.ok().body(scheduleService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<DeliverySchedule> createDeliveryDate(@Valid @RequestBody DeliverySchedule deliverySchedule) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/schedule").toUriString());
        return ResponseEntity.created(uri).body(scheduleService.create(deliverySchedule));
    }

    @PutMapping
    public ResponseEntity<DeliverySchedule> updateDeliveryDate(@Valid @RequestBody DeliverySchedule deliverySchedule) {
        return ResponseEntity.ok().body(scheduleService.update(deliverySchedule));
    }
}
