package com.huisa.service;

import com.huisa.dto.DeliveryAddressRequestDto;
import com.huisa.dto.DeliveryAddressResponseDto;

public interface DeliveryAddressService {


    DeliveryAddressResponseDto createDeliveryAddress(
            DeliveryAddressRequestDto requestDto,
            Long orderId
    );

    DeliveryAddressResponseDto getDeliveryAddressByOrderId(Long orderId);

    void deleteDeliveryAddressByOrderId(Long orderId);

}
