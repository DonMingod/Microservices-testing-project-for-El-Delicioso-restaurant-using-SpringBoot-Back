package com.huisa.service;

import com.huisa.dtos.TicketItemResponseDto;
import com.huisa.enums.KitchenItemStatus;

import java.util.List;

public interface TicketItemService {

    TicketItemResponseDto getTicketItemById(Long itemId);

    List<TicketItemResponseDto> getItemsByTicketId(Long ticketId);

    TicketItemResponseDto updateItemStatus(
            Long itemId,
            KitchenItemStatus status
    );
}
