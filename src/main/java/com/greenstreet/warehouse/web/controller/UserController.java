package com.greenstreet.warehouse.web.controller;

import com.greenstreet.warehouse.entity.User;
import com.greenstreet.warehouse.model.request.RequestUserDTO;
import com.greenstreet.warehouse.model.response.ResponseUserDTO;
import com.greenstreet.warehouse.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "User", description = "User management")
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<Page<ResponseUserDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok().body(userService.getUsers(pageable));
    }

    @GetMapping("/{login}")
    public ResponseEntity<Optional<User>> getUserByLogin(@PathVariable(name = "login") String login) {
        return ResponseEntity.ok().body(userService.getUser(login));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody RequestUserDTO userDTO) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/user").toUriString());
        return ResponseEntity.created(uri).body(userService.createUser(userDTO));
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@Valid @RequestBody RequestUserDTO userDTO) {
        return ResponseEntity.ok().body(userService.updateUser(userDTO));
    }

}
