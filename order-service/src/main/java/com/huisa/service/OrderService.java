package com.huisa.service;

import com.huisa.dto.OrderRequestDto;
import com.huisa.dto.OrderResponseDto;
import com.huisa.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {

    OrderResponseDto createOrder(
            OrderRequestDto orderRequestDto,
            Long userId
    );

    OrderResponseDto getOrderById(
            Long orderId,
            Long userId
    );

    Page<OrderResponseDto> getOrdersByUserId(
            Long userId,
            Pageable pageable
    );

    OrderResponseDto updateOrderStatus(
            Long orderId,
            OrderStatus newStatus,
            Long changedBy
    );
}
