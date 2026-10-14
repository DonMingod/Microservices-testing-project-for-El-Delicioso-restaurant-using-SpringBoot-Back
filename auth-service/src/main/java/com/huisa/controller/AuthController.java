package com.huisa.controller;

import com.huisa.dto.LoginRequestDto;
import com.huisa.dto.LoginResponseDto;
import com.huisa.dto.UserRequestDto;
import com.huisa.dto.UserResponseDto;
import com.huisa.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto request)
    {
        return ResponseEntity.ok( authService.login(request) );
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserRequestDto request)
    { return ResponseEntity .status(HttpStatus.CREATED) .body(authService.register(request));
    }
}
