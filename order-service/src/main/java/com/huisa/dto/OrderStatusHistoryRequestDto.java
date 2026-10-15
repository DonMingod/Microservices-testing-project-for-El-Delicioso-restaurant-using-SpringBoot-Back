package com.huisa.dto;

import com.huisa.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderStatusHistoryRequestDto(

        @NotNull(message = "El ID del pedido es obligatorio")
        @Positive(message = "El ID del pedido debe ser positivo")
        Long orderId,

        @NotNull(message = "El nuevo estado es obligatorio")
        OrderStatus status,

        @Positive(message = "El ID del usuario que realiza el cambio debe ser positivo")
        Long changedBy
) {
}
