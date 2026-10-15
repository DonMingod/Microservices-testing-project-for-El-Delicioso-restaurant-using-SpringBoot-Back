package com.huisa.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record OrderItemResponseDto(

        Long id,
        Long menuItemId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) {
}
