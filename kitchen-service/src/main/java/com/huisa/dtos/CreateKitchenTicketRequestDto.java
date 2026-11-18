package com.huisa.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateKitchenTicketRequestDto(
        @NotNull(message = "Order ID is required")
        @Positive(message = "Order ID must be positive")
        Long orderId
) {
}
