package com.huisa.service.impl;

import com.huisa.dto.DeliveryAddressRequestDto;
import com.huisa.dto.DeliveryAddressResponseDto;
import com.huisa.model.DeliveryAddress;
import com.huisa.model.Order;
import com.huisa.repository.DeliveryAddressRepository;
import com.huisa.repository.OrderRepository;
import com.huisa.service.DeliveryAddressService;
import com.huisa.util.DeliveryAddressMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class DeliveryAddressServiceImpl implements DeliveryAddressService {

    private final DeliveryAddressRepository deliveryAddressRepository;
    private final OrderRepository orderRepository;
    private final DeliveryAddressMapper deliveryAddressMapper;


    @Override
    public DeliveryAddressResponseDto createDeliveryAddress(
            DeliveryAddressRequestDto requestDto,
            Long orderId) {

        if (requestDto == null) {
            throw new IllegalArgumentException(
                    "Los datos de la dirección son obligatorios"
            );
        }

        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException(
                    "El ID del pedido debe ser positivo"
            );
        }

        if (deliveryAddressRepository.existsByOrderId(orderId)) {
            throw new IllegalArgumentException(
                    "El pedido ya tiene una dirección de entrega"
            );
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró el pedido con ID: " + orderId
                ));

        DeliveryAddress address =
                deliveryAddressMapper.toEntity(requestDto);

        address.setOrder(order);

        DeliveryAddress savedAddress =
                deliveryAddressRepository.save(address);

        return deliveryAddressMapper.toDto(savedAddress);
    }

    @Override
    @Transactional
    public DeliveryAddressResponseDto getDeliveryAddressByOrderId(
            Long orderId) {

        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException(
                    "El ID del pedido debe ser positivo"
            );
        }

        DeliveryAddress address =
                deliveryAddressRepository.findByOrderId(orderId)
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No se encontró una dirección para el pedido: "
                                        + orderId
                        ));

        return deliveryAddressMapper.toDto(address);
    }

    @Override
    public void deleteDeliveryAddressByOrderId(Long orderId) {
        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException(
                    "El ID del pedido debe ser positivo"
            );
        }

        if (!deliveryAddressRepository.existsByOrderId(orderId)) {
            throw new EntityNotFoundException(
                    "No existe una dirección para el pedido: " + orderId
            );
        }

        deliveryAddressRepository.deleteByOrderId(orderId);
    }


}
