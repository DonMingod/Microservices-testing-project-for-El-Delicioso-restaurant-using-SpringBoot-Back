package com.huisa.util;

import com.huisa.dtos.TicketItemResponseDto;
import com.huisa.model.TicketItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface KitchenTicketMapper {
    @Mapping(source = "startAt", target = "startedAt")
    TicketItemResponseDto toDto(TicketItem ticketItem);
}
