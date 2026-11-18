package com.huisa.service;

import com.huisa.dtos.KitchenTicketResponseDto;
import com.huisa.dtos.TicketItemResponseDto;
import com.huisa.enums.KitchenItemStatus;
import com.huisa.enums.KitchenTicketStatus;

import java.util.List;

public interface KitchenTicketService {

    KitchenTicketResponseDto createTicket(Long orderId);

    KitchenTicketResponseDto getTicketById(Long ticketId);

    KitchenTicketResponseDto getTicketByOrderId(Long orderId);

    List<KitchenTicketResponseDto> getTicketsByStatus(KitchenTicketStatus status);

    KitchenTicketResponseDto updateTicketStatus(
            Long ticketId,
            KitchenTicketStatus status
    );

    TicketItemResponseDto updateTicketItemStatus(
            Long ticketId,
            Long itemId,
            KitchenItemStatus status
    );


}
