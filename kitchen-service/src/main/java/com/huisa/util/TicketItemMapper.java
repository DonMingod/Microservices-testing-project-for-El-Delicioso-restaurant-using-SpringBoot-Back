package com.huisa.util;


import com.huisa.dtos.KitchenTicketResponseDto;
import com.huisa.model.KitchenTicket;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = TicketItemMapper.class
)
public interface TicketItemMapper {

    KitchenTicketResponseDto toDto(KitchenTicket kitchenTicket);

    List<KitchenTicketResponseDto> toDtoList(List<KitchenTicket> kitchenTickets);
}
