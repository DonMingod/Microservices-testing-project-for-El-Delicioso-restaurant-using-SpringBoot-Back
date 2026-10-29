package com.huisa.service.impl;

import com.huisa.dto.*;
import com.huisa.enums.OrderStatus;
import com.huisa.model.Order;
import com.huisa.repository.OrderRepository;
import com.huisa.service.DeliveryAddressService;
import com.huisa.service.OrderItemService;
import com.huisa.service.OrderService;
import com.huisa.service.OrderStatusHistoryService;
import com.huisa.util.OrderMapper;
import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl  implements OrderService {

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final OrderItemService orderItemService;
    private final DeliveryAddressService deliveryAddressService;
    private final OrderStatusHistoryService orderStatusHistoryService;

    @Override
    public OrderResponseDto createOrder(
            OrderRequestDto orderRequestDto,
            Long userId) {

        if (orderRequestDto == null) {
            throw new IllegalArgumentException(
                    "La solicitud del pedido es obligatoria"
            );
        }

        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException(
                    "El ID del usuario debe ser positivo"
            );
        }

        Order order = orderMapper.toEntity(orderRequestDto);

        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING);

        // Inicializar los importes antes del primer INSERT.
        order.setSubtotal(ZERO);
        order.setTaxAmount(ZERO);
        order.setDiscountAmount(ZERO);
        order.setDeliveryFee(ZERO);
        order.setTotalAmount(ZERO);

        Order savedOrder = orderRepository.save(order);

        // Guardar los productos con precios obtenidos de menu-service.
        List<OrderItemResponseDto> items =
                orderItemService.createOrderItems(
                        orderRequestDto.items(),
                        savedOrder
                );

        BigDecimal subtotal = items.stream()
                .map(OrderItemResponseDto::totalPrice)
                .reduce(ZERO, BigDecimal::add);

        // No se inventa una tasa tributaria ni una tarifa de envío.
        // Ambas permanecen en cero hasta definir las reglas del negocio.
        BigDecimal taxAmount = ZERO;
        BigDecimal discountAmount = ZERO;
        BigDecimal deliveryFee = ZERO;

        BigDecimal totalAmount = subtotal
                .add(taxAmount)
                .subtract(discountAmount)
                .add(deliveryFee);

        savedOrder.setSubtotal(subtotal);
        savedOrder.setTaxAmount(taxAmount);
        savedOrder.setDiscountAmount(discountAmount);
        savedOrder.setDeliveryFee(deliveryFee);
        savedOrder.setTotalAmount(totalAmount);

        savedOrder = orderRepository.save(savedOrder);

        // La dirección se asocia al ID generado para el pedido.
        deliveryAddressService.createDeliveryAddress(
                orderRequestDto.deliveryAddress(),
                savedOrder.getId()
        );

        // Registrar el estado inicial: no existe un estado anterior.
        orderStatusHistoryService.recordStatusChange(
                savedOrder,
                null,
                OrderStatus.PENDING,
                userId
        );

        return buildOrderResponse(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId, Long userId) {

        if (orderId == null || orderId <= 0
                || userId == null || userId <= 0) {
            throw new IllegalArgumentException(
                    "El pedido y el usuario deben tener IDs positivos"
            );
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró el pedido con ID: " + orderId
                ));

        if (!userId.equals(order.getUserId())) {
            throw new IllegalArgumentException(
                    "No tienes permiso para consultar este pedido"
            );
        }

        return buildOrderResponse(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderResponseDto> getOrdersByUserId(
            Long userId,
            Pageable pageable) {

        if (userId == null || userId <= 0 || pageable == null) {
            throw new IllegalArgumentException(
                    "El usuario y la paginación son obligatorios"
            );
        }

        return orderRepository.findByUserId(userId, pageable)
                .map(this::buildOrderResponse);
    }

    @Override
    public OrderResponseDto updateOrderStatus(
            Long orderId,
            OrderStatus newStatus,
            Long changedBy) {

        if (orderId == null || orderId <= 0
                || newStatus == null
                || changedBy == null || changedBy <= 0) {
            throw new IllegalArgumentException(
                    "El pedido, el nuevo estado y el usuario son obligatorios"
            );
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró el pedido con ID: " + orderId
                ));

        OrderStatus oldStatus = order.getStatus();

        if (oldStatus == newStatus) {
            throw new IllegalArgumentException(
                    "El pedido ya tiene ese estado"
            );
        }

        // Registrar el cambio dentro de la misma transacción.
        orderStatusHistoryService.recordStatusChange(
                order,
                oldStatus,
                newStatus,
                changedBy
        );

        order.setStatus(newStatus);

        Order updatedOrder = orderRepository.save(order);

        return buildOrderResponse(updatedOrder);
    }

    private OrderResponseDto buildOrderResponse(Order order) {

        OrderResponseDto base = orderMapper.toDto(order);

        List<OrderItemResponseDto> items =
                orderItemService.getOrderItemsByOrderId(order.getId());

        DeliveryAddressResponseDto address =
                deliveryAddressService.getDeliveryAddressByOrderId(
                        order.getId()
                );

        List<OrderStatusHistoryResponseDto> history =
                orderStatusHistoryService.getHistoryByOrderId(
                        order.getId()
                );

        return OrderResponseDto.builder()
                .id(base.id())
                .userId(base.userId())
                .deliveryPersonId(base.deliveryPersonId())
                .subtotal(base.subtotal())
                .taxAmount(base.taxAmount())
                .discountAmount(base.discountAmount())
                .deliveryFee(base.deliveryFee())
                .totalAmount(base.totalAmount())
                .status(base.status())
                .paymentMethod(base.paymentMethod())
                .paymentStatus(base.paymentStatus())
                .notes(base.notes())
                .estimatedDeliveryTime(base.estimatedDeliveryTime())
                .createdAt(base.createdAt())
                .updatedAt(base.updatedAt())
                .items(items)
                .deliveryAddress(address)
                .statusHistory(history)
                .build();
    }
}
