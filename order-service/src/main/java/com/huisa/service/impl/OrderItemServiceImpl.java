package com.huisa.service.impl;

import com.huisa.client.MenuClient;
import com.huisa.dto.MenuItemResponseDto;
import com.huisa.dto.OrderItemRequestDto;
import com.huisa.dto.OrderItemResponseDto;
import com.huisa.dto.OrderRequestDto;
import com.huisa.model.Order;
import com.huisa.model.OrderItem;
import com.huisa.repository.OrderItemRepository;
import com.huisa.service.OrderItemService;
import com.huisa.util.OrderItemMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemServiceImpl  implements OrderItemService {


    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final MenuClient menuClient;

    @Override
    public List<OrderItemResponseDto> createOrderItems(
            List<OrderItemRequestDto> items,
            Order order) {

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException(
                    "El pedido debe contener al menos un producto"
            );
        }

        if (order == null || order.getId() == null) {
            throw new IllegalArgumentException(
                    "El pedido debe estar guardado antes de agregar productos"
            );
        }

        List<OrderItem> orderItems = items.stream()
                .map(request -> {
                    if (request == null
                            || request.menuItemId() == null
                            || request.quantity() == null
                            || request.quantity() <= 0) {
                        throw new IllegalArgumentException(
                                "El producto y su cantidad son obligatorios"
                        );
                    }

                    MenuItemResponseDto menuItem =
                            menuClient.getMenuItemById(request.menuItemId());

                    if (menuItem == null) {
                        throw new EntityNotFoundException(
                                "No se encontró el producto con ID: "
                                        + request.menuItemId()
                        );
                    }

                    if (!Boolean.TRUE.equals(menuItem.isAvailable())) {
                        throw new IllegalArgumentException(
                                "El producto no está disponible: "
                                        + menuItem.name()
                        );
                    }

                    if (menuItem.stock() != null
                            && request.quantity() > menuItem.stock()) {
                        throw new IllegalArgumentException(
                                "Stock insuficiente para el producto: "
                                        + menuItem.name()
                        );
                    }

                    if (menuItem.price() == null
                            || !Double.isFinite(menuItem.price())
                            || menuItem.price() < 0) {
                        throw new IllegalStateException(
                                "El precio del producto no es válido"
                        );
                    }

                    OrderItem orderItem = orderItemMapper.toEntity(request);
                    orderItem.setOrder(order);
                    orderItem.setUnitPrice(
                            BigDecimal.valueOf(menuItem.price())
                    );

                    return orderItem;
                })
                .toList();

        return orderItemRepository.saveAll(orderItems)
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public List<OrderItemResponseDto> getOrderItemsByOrderId(Long orderId) {
        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException(
                    "El ID del pedido debe ser positivo"
            );
        }

        return orderItemRepository.findByOrderId(orderId)
                .stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public void deleteOrderItemsByOrderId(Long orderId) {
        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException(
                    "El ID del pedido debe ser positivo"
            );
        }

        orderItemRepository.deleteByOrderId(orderId);
    }


}
