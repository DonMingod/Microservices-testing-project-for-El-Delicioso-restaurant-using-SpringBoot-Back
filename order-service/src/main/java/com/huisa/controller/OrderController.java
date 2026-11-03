package com.huisa.controller;

import com.huisa.dto.OrderRequestDto;
import com.huisa.dto.OrderResponseDto;
import com.huisa.enums.OrderStatus;
import com.huisa.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @Valid @RequestBody OrderRequestDto request,
            HttpServletRequest httpRequest) {

        Long currentUserId = resolveCurrentUserId(httpRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.createOrder(request, currentUserId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(
            @PathVariable Long id,
            HttpServletRequest httpRequest) {

        Long currentUserId = resolveCurrentUserId(httpRequest);

        return ResponseEntity.ok(orderService.getOrderById(id, currentUserId));
    }

    @GetMapping("/me")
    public ResponseEntity<Page<OrderResponseDto>> getMyOrders(
            HttpServletRequest httpRequest,
            Pageable pageable) {

        Long currentUserId = resolveCurrentUserId(httpRequest);

        return ResponseEntity.ok(orderService.getOrdersByUserId(currentUserId, pageable));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, Object> payload,
            HttpServletRequest httpRequest) {

        Long currentUserId = resolveCurrentUserId(httpRequest);
        OrderStatus resolvedStatus = resolveStatus(payload);

        return ResponseEntity.ok(
                orderService.updateOrderStatus(id, resolvedStatus, currentUserId)
        );
    }

    private OrderStatus resolveStatus(Map<String, Object> payload) {
        if (payload == null || payload.isEmpty()) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }

        Object statusValue = payload.get("status");

        if (statusValue instanceof OrderStatus status) {
            return status;
        }

        if (statusValue instanceof String value) {
            return OrderStatus.valueOf(value.trim().toUpperCase());
        }

        throw new IllegalArgumentException("El estado no es válido");
    }

    private Long resolveCurrentUserId(HttpServletRequest request) {
        Object userId = request.getAttribute("userId");
        if (userId == null) {
            userId = request.getHeader("X-Auth-User-Id");
        }

        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }

        if (userId instanceof Number number) {
            return number.longValue();
        }

        try {
            return Long.parseLong(userId.toString());
        } catch (NumberFormatException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no autenticado");
        }
    }
}