package com.huisa.dto;

import lombok.Builder;

@Builder
public record LoginResponseDto(
        String token,
        String type,
        Long userId,
        String email,
        String name
) { }
