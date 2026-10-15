package com.huisa.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DeliveryAddressResponseDto(
        Long id,
        String addressLine1,
        String city,
        String country,
        String phone,
        String referenceNote,
        LocalDateTime createdAt
) {
}
