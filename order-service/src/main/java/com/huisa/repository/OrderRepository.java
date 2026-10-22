package com.huisa.repository;

import com.huisa.enums.OrderStatus;
import com.huisa.enums.PaymentStatus;
import com.huisa.model.Order;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<@NonNull Order, @NonNull Long> {


    Page<Order> findByUserId(Long userId, Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Page<Order> findByPaymentStatus(
            PaymentStatus paymentStatus,
            Pageable pageable
    );
}

