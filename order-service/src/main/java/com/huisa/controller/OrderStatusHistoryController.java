package com.huisa.controller;

import com.huisa.dto.OrderStatusHistoryResponseDto;
import com.huisa.model.Order;
import com.huisa.repository.OrderRepository;
import com.huisa.service.OrderStatusHistoryService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderStatusHistoryController {

    private final OrderStatusHistoryService orderStatusHistoryService;
    private final OrderRepository orderRepository;

    @GetMapping("/{orderId}/status-history")
    public ResponseEntity<List<OrderStatusHistoryResponseDto>> getStatusHistoryByOrderId(
            @PathVariable Long orderId,
            HttpServletRequest request) {
        authorizeOrderAccess(orderId, request);

        return ResponseEntity.ok(
                orderStatusHistoryService.getHistoryByOrderId(orderId)
        );
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
                    "No tienes permiso para consultar el historial de este pedido"
            );
        }
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
