package com.huisa.service;

import com.huisa.dto.OrderItemRequestDto;
import com.huisa.dto.OrderItemResponseDto;
import com.huisa.dto.OrderRequestDto;
import com.huisa.model.Order;

import java.util.List;

public interface OrderItemService {


    List<OrderItemResponseDto> createOrderItems(
            List<OrderItemRequestDto> items,
            Order order
    );


    List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId);

    void deleteOrderItemsByOrderId(Long orderId);

}
