package com.huisa.service.impl;

import com.huisa.dto.OrderStatusHistoryResponseDto;
import com.huisa.enums.OrderStatus;
import com.huisa.model.Order;
import com.huisa.model.OrderStatusHistory;
import com.huisa.repository.OrderStatusHistoryRepository;
import com.huisa.service.OrderStatusHistoryService;
import com.huisa.util.OrderStatusHistoryMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderStatusHistoryServiceImp implements OrderStatusHistoryService {

    private final OrderStatusHistoryRepository historyRepository;
    private final OrderStatusHistoryMapper historyMapper;

    @Override
    public OrderStatusHistoryResponseDto recordStatusChange(
            Order order,
            OrderStatus oldStatus,
            OrderStatus newStatus,
            Long changedBy) {

        if (order == null || order.getId() == null) {
            throw new IllegalArgumentException(
                    "El pedido debe existir para registrar su historial"
            );
        }
         if (oldStatus == null) {
            throw new IllegalArgumentException(
                    "El estado anterior es obligatorio"
            );
        }
         if (newStatus == null) {
            throw new IllegalArgumentException(
                    "El nuevo estado es obligatorio"
            );
        }

        if (changedBy == null || changedBy <= 0) {
            throw new IllegalArgumentException(
                    "El ID del usuario debe ser positivo"
            );
        }

        if (oldStatus == newStatus) {
            throw new IllegalArgumentException(
                    "El pedido ya tiene ese estado"
            );
        }

        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setChangeBy(changedBy);

        OrderStatusHistory savedHistory =
                historyRepository.save(history);

        return historyMapper.toDto(savedHistory);
    }

    @Override
    @Transactional
    public List<OrderStatusHistoryResponseDto> getHistoryByOrderId(
            Long orderId) {

        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException(
                    "El ID del pedido debe ser positivo"
            );
        }

        return historyRepository
                .findByOrderIdOrderByChangeAtAsc(orderId)
                .stream()
                .map(historyMapper::toDto)
                .toList();
    }
}
