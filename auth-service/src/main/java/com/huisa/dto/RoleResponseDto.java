package com.huisa.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record RoleResponseDto(
        Long id,

        String name,

        String description,

        LocalDateTime createdAt
) {}
