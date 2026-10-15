package com.huisa.repository;

import com.huisa.model.DeliveryAddress;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryAddressRepository extends JpaRepository<@NonNull DeliveryAddress,@NonNull Long> {


    Optional<DeliveryAddress> findByOrderId(Long orderId);

    boolean existsByOrderId(Long orderId);

    void deleteByOrderId(Long orderId);
}
