package com.huisa.util;

import com.huisa.dto.OrderItemRequestDto;
import com.huisa.dto.OrderItemResponseDto;
import com.huisa.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {


    @Mapping( target = "order", ignore = true)
    @Mapping( target = "unitPrice", ignore = true)
    OrderItem toEntity(OrderItemRequestDto requestDto);

    @Mapping(
            target = "totalPrice",
            expression = "java(orderItem.getUnitPrice().multiply(java.math.BigDecimal.valueOf(orderItem.getQuantity())))"
    )
    OrderItemResponseDto toDto(OrderItem orderItem);

    List<OrderItemResponseDto> toDtoList(List<OrderItem> orderItems);
}
