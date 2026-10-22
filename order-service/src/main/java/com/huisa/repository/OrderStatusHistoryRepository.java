package com.huisa.repository;

import com.huisa.model.OrderStatusHistory;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderStatusHistoryRepository extends JpaRepository<@NonNull OrderStatusHistory, @NonNull Long> {

    List<OrderStatusHistory> findByOrderIdOrderByChangeAtAsc(Long orderId);
}
