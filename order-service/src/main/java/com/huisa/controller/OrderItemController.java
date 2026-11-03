package com.huisa.controller;

import com.huisa.dto.OrderItemRequestDto;
import com.huisa.dto.OrderItemResponseDto;
import com.huisa.model.Order;
import com.huisa.repository.OrderRepository;
import com.huisa.service.OrderItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderRepository orderRepository;

    @GetMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItemResponseDto>> getItemsByOrderId(
            @PathVariable Long orderId,
            HttpServletRequest request) {

        authorizeOrderAccess(orderId, request);

        return ResponseEntity.ok(orderItemService.getOrderItemsByOrderId(orderId));
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<List<OrderItemResponseDto>> addItemsToOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody List<@NotNull(message = "El item no puede ser nulo") OrderItemRequestDto> items,
            HttpServletRequest request) {

        Order order = validateAndLoadOrder(orderId, request);

        List<OrderItemResponseDto> createdItems =
                orderItemService.createOrderItems(items, order);

        orderItemService.refreshOrderTotals(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdItems);
    }

    @DeleteMapping("/{orderId}/items")
    public ResponseEntity<Void> deleteItemsByOrderId(
            @PathVariable Long orderId,
            HttpServletRequest request) {

        validateAndLoadOrder(orderId, request);

        orderItemService.deleteOrderItemsByOrderId(orderId);
        orderItemService.refreshOrderTotals(orderId);

        return ResponseEntity.noContent().build();
    }

    private Order validateAndLoadOrder(Long orderId, HttpServletRequest request) {
        Long currentUserId = resolveCurrentUserId(request);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró el pedido con ID: " + orderId
                ));

        if (!currentUserId.equals(order.getUserId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tienes permiso para modificar este pedido"
            );
        }

        return order;
    }

    private void authorizeOrderAccess(Long orderId, HttpServletRequest request) {
        Long currentUserId = resolveCurrentUserId(request);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró el pedido con ID: " + orderId
                ));

        if (!currentUserId.equals(order.getUserId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tienes permiso para consultar este pedido"
            );
        }
    }

    private Long resolveCurrentUserId(HttpServletRequest request) {
        Object userId = request.getAttribute("userId");
        if (userId == null) {
            userId = request.getHeader("X-Auth-User-Id");
        }

        if (userId == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuario no autenticado"
            );
        }

        if (userId instanceof Number number) {
            return number.longValue();
        }

        try {
            return Long.parseLong(userId.toString());
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Usuario no autenticado"
            );
        }
    }
}
