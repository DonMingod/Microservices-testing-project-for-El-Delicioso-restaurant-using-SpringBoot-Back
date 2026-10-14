package com.huisa.controller;


import com.huisa.dto.RoleRequestDto;
import com.huisa.dto.RoleResponseDto;
import com.huisa.service.RoleService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<RoleResponseDto> create (@Valid @RequestBody RoleRequestDto request) {
       RoleResponseDto response = roleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleResponseDto> findById (@PathVariable Long id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<RoleResponseDto>> findAll() {
        return ResponseEntity.ok( roleService.findAll() );
    }
    @PutMapping("/{id}")
    public ResponseEntity<RoleResponseDto> update( @PathVariable Long id, @Valid @RequestBody RoleRequestDto request)
    { return ResponseEntity.ok( roleService.update(id, request) );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete( @PathVariable Long id) {
        roleService.delete(id); return ResponseEntity.noContent().build();
    }

}