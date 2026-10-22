package com.huisa.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MenuItemResponseDto(
        Long id,
        String categoryName,
        String name,
        String description,
        Double price,
        Integer stock,
        Boolean isAvailable
) {
}
