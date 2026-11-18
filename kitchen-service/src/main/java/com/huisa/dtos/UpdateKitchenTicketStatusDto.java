package com.huisa.dtos;

import com.huisa.enums.KitchenItemStatus;
import com.huisa.enums.KitchenTicketStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateKitchenTicketStatusDto(
        @NotNull(message = "Ticket status is required")
        KitchenTicketStatus status
) {
}
