package com.huisa.dtos.orderclient;

import java.util.List;

public record OrderResponseDto(
        Long id,
        String status,
        List<OrderItemResponseDto> items
) {
}
