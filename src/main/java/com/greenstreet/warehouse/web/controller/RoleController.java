package com.greenstreet.warehouse.web.controller;

import com.greenstreet.warehouse.entity.Role;
import com.greenstreet.warehouse.services.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
@Tag(name = "Role", description = "Role management for users")
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> getAll() {
        return ResponseEntity.ok().body(roleService.getAll());
    }

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/role").toUriString());
        return ResponseEntity.created(uri).body(roleService.createRole(role));
    }
}
