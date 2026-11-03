package com.huisa.controller;

import com.huisa.dto.DeliveryAddressRequestDto;
import com.huisa.dto.DeliveryAddressResponseDto;
import com.huisa.model.Order;
import com.huisa.repository.OrderRepository;
import com.huisa.service.DeliveryAddressService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class DeliveryAddressController {

    private final DeliveryAddressService deliveryAddressService;
    private final OrderRepository orderRepository;

    @PostMapping("/{orderId}/delivery-address")
    public ResponseEntity<DeliveryAddressResponseDto> createDeliveryAddress(
            @PathVariable Long orderId,
            @Valid @RequestBody DeliveryAddressRequestDto request,
            HttpServletRequest httpRequest) {

        Order order = authorizeAndLoadOrder(orderId, httpRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deliveryAddressService.createDeliveryAddress(request, order.getId()));
    }

    @GetMapping("/{orderId}/delivery-address")
    public ResponseEntity<DeliveryAddressResponseDto> getDeliveryAddressByOrderId(
            @PathVariable Long orderId,
            HttpServletRequest httpRequest) {

        authorizeAndLoadOrder(orderId, httpRequest);

        return ResponseEntity.ok(
                deliveryAddressService.getDeliveryAddressByOrderId(orderId)
        );
    }

    @DeleteMapping("/{orderId}/delivery-address")
    public ResponseEntity<Void> deleteDeliveryAddressByOrderId(
            @PathVariable Long orderId,
            HttpServletRequest httpRequest) {

        authorizeAndLoadOrder(orderId, httpRequest);

        deliveryAddressService.deleteDeliveryAddressByOrderId(orderId);

        return ResponseEntity.noContent().build();
    }

    private Order authorizeAndLoadOrder(Long orderId, HttpServletRequest request) {
        Long currentUserId = resolveCurrentUserId(request);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No se encontró el pedido con ID: " + orderId
                ));

        if (!currentUserId.equals(order.getUserId())) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "No tienes permiso para acceder a esta dirección"
            );
        }

        return order;
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
