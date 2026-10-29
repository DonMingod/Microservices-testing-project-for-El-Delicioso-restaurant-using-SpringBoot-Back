package com.huisa.service;

import com.huisa.dto.OrderStatusHistoryResponseDto;
import com.huisa.enums.OrderStatus;
import com.huisa.model.Order;

import java.util.List;

public interface OrderStatusHistoryService {

    OrderStatusHistoryResponseDto recordStatusChange(
            Order order,
            OrderStatus oldStatus,
            OrderStatus newStatus,
            Long changedBy
    );

    List<OrderStatusHistoryResponseDto> getHistoryByOrderId(Long orderId);

}
