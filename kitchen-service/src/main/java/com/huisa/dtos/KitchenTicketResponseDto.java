package com.huisa.dtos;

import com.huisa.enums.KitchenTicketStatus;

import java.time.LocalDateTime;
import java.util.List;

public record KitchenTicketResponseDto(
        Long id,
        Long orderId,
        KitchenTicketStatus status,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<TicketItemResponseDto> items
) {
}
