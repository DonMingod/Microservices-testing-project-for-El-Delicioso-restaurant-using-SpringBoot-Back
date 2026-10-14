package com.huisa.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record MenuItemRequestDto(

        @NotNull
        Long categoryId,

        @NotBlank
        @Size(max = 150)
        String name,

        String description,

        @NotNull
        @DecimalMin(value = "0.01")
        BigDecimal price,

        Integer stock,

        Boolean available
) {
}
