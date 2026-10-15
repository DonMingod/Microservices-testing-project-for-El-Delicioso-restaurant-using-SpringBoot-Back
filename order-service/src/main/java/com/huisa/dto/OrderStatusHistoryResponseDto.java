package com.huisa.dto;

import com.huisa.enums.OrderStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record OrderStatusHistoryResponseDto(

        Long id,
        OrderStatus oldStatus,
        OrderStatus newStatus,
        Long changedBy,
        LocalDateTime changedAt
) {
}
