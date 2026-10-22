package com.huisa.util;

import com.huisa.dto.OrderRequestDto;
import com.huisa.dto.OrderResponseDto;
import com.huisa.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper (componentModel = "spring")
public interface OrderMapper {


    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "deliveryPersonId", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "subtotal", ignore = true)
    @Mapping(target = "taxAmount", ignore = true)
    @Mapping(target = "discountAmount", ignore = true)
    @Mapping(target = "deliveryFee", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "paymentMethod", ignore = true)
    @Mapping(target = "paymentStatus", ignore = true)
    @Mapping(target = "estimatedDeliveryTime", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Order toEntity(OrderRequestDto requestDto);

    @Mapping(target = "items", ignore = true)
    @Mapping(target = "deliveryAddress", ignore = true)
    @Mapping(target = "statusHistory", ignore = true)
    OrderResponseDto toDto(Order order);

    List<OrderResponseDto> toDtoList(List<Order> orders);
}
