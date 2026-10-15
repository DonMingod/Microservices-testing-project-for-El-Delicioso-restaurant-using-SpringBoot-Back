package com.huisa.repository;

import com.huisa.model.OrderItem;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository  extends JpaRepository<@NonNull OrderItem,@NonNull Long> {

    List<OrderItem> findByOrderId(Long orderId);

    long countByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);


}
