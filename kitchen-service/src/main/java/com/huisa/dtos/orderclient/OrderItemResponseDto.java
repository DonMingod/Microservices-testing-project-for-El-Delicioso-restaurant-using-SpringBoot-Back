package com.huisa.dtos.orderclient;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        Long menuItemId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) {
}
