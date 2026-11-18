package com.huisa.dtos;

import com.huisa.enums.KitchenItemStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTicketItemStatusDto(
        @NotNull(message = "Ticket item status is required")
        KitchenItemStatus status
) {
}
