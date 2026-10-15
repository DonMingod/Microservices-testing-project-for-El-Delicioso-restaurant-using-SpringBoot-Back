package com.huisa.dto;

import com.huisa.enums.OrderStatus;
import com.huisa.enums.PaymentMethod;
import com.huisa.enums.PaymentStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponseDto(

        Long id,
        Long userId,
        Long deliveryPersonId,
        BigDecimal subtotal,
        BigDecimal taxAmount,
        BigDecimal discountAmount,
        BigDecimal deliveryFee,
        BigDecimal totalAmount,
        OrderStatus status,
        PaymentMethod paymentMethod,
        PaymentStatus paymentStatus,
        String notes,
        LocalDateTime estimatedDeliveryTime,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<OrderItemResponseDto> items,
        DeliveryAddressResponseDto deliveryAddress,
        List<OrderStatusHistoryResponseDto> statusHistory
) {
}
