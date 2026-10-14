package com.huisa.controller;


import com.huisa.dto.MenuItemRequestDto;
import com.huisa.dto.MenuItemResponseDto;
import com.huisa.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu-items")
@RequiredArgsConstructor
public class MenuItemController {

    private final MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemResponseDto> create(
            @Valid @RequestBody MenuItemRequestDto request) {

        MenuItemResponseDto response = menuItemService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemResponseDto> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(menuItemService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemResponseDto>> findAll() {

        return ResponseEntity.ok(menuItemService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MenuItemResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody MenuItemRequestDto request) {

        return ResponseEntity.ok(menuItemService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        menuItemService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
