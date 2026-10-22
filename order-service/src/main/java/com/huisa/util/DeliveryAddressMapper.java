package com.huisa.util;


import com.huisa.dto.DeliveryAddressRequestDto;
import com.huisa.dto.DeliveryAddressResponseDto;
import com.huisa.model.DeliveryAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface DeliveryAddressMapper {


    @Mapping(target = "order", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    DeliveryAddress toEntity(DeliveryAddressRequestDto requestDto);

    DeliveryAddressResponseDto toDto(DeliveryAddress deliveryAddress);

    List<DeliveryAddressResponseDto> toDtoList(
            List<DeliveryAddress> deliveryAddresses
    );

}
