package com.huisa.dto;

import com.huisa.model.Category;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MenuItemResponseDto(
        Long id,
        Category category,
        String categoryName,
        String name,
        String description,
        Double price,
        Integer stock,
        Boolean isAvailable,
        LocalDateTime  createdAt,
        LocalDateTime updatedAt
) {
}
