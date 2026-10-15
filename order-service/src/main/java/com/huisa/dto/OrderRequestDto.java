package com.huisa.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record OrderRequestDto(

        @NotEmpty(message = "El pedido debe contener al menos un producto")
        List<@NotNull(message = "El producto no puede ser nulo")
                @Valid OrderItemRequestDto> items,

        @NotNull(message = "La dirección de entrega es obligatoria")
        @Valid DeliveryAddressRequestDto deliveryAddress,

        @Size(max = 2000, message = "Las notas no pueden superar los 2000 caracteres")
        String notes
) {
}
