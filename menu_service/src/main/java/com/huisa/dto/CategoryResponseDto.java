package com.huisa.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CategoryResponseDto(

        Long id,
        String name,
        String description,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
)
{}
