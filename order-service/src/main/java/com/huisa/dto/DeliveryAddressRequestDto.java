package com.huisa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record DeliveryAddressRequestDto(

        @NotBlank(message = "La dirección es obligatoria")
        @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
        String addressLine1,

        @NotBlank(message = "La ciudad es obligatoria")
        @Size(max = 100, message = "La ciudad no puede superar los 100 caracteres")
        String city,

        @NotBlank(message = "El país es obligatorio")
        @Size(max = 100, message = "El país no puede superar los 100 caracteres")
        String country,

        @NotBlank(message = "El teléfono es obligatorio")
        @Size(max = 20, message = "El teléfono no puede superar los 20 caracteres")
        String phone,

        String referenceNote

) {
}
