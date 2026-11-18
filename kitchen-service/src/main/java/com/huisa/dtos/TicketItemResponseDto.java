package com.huisa.dtos;

import com.huisa.enums.KitchenItemStatus;

import java.time.LocalDateTime;

public record TicketItemResponseDto(
        Long id,
        Long menuItemId,
        Integer quantity,
        KitchenItemStatus status,
        String notes,
        LocalDateTime startedAt,
        LocalDateTime completedAt
) {
}
