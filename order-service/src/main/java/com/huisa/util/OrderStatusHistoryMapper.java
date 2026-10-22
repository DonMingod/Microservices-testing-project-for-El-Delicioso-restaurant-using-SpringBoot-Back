package com.huisa.util;

import com.huisa.dto.OrderStatusHistoryRequestDto;
import com.huisa.dto.OrderStatusHistoryResponseDto;
import com.huisa.model.OrderStatusHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderStatusHistoryMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "oldStatus", ignore = true)
    @Mapping(target = "changeAt", ignore = true)
    @Mapping(target = "changeBy", source = "changedBy")
    OrderStatusHistory toEntity(
            OrderStatusHistoryRequestDto requestDto
    );

    @Mapping(target = "changedBy", source = "changeBy")
    @Mapping(target = "changedAt", source = "changeAt")
    OrderStatusHistoryResponseDto toDto(
            OrderStatusHistory history
    );

    List<OrderStatusHistoryResponseDto> toDtoList(
            List<OrderStatusHistory> historyList
    );

}
