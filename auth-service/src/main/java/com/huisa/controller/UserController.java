package com.huisa.controller;


import com.huisa.dto.UserRequestDto;
import com.huisa.dto.UserResponseDto;
import com.huisa.service.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<@NonNull UserResponseDto> create(UserRequestDto request) {
        UserResponseDto response = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull UserResponseDto> findById( @PathVariable Long id) {
       UserResponseDto response = userService.findById(id);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<@NonNull List<UserResponseDto>> findAll() {

        List<UserResponseDto> response = userService.findAll();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<@NonNull UserResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto request) {

        UserResponseDto response = userService.update(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull Void> delete(
            @PathVariable Long id) {

        userService.delete(id);

        return ResponseEntity.noContent().build();
    }




}