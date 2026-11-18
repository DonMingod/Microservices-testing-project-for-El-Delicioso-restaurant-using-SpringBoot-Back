package com.huisa.repository;

import com.huisa.enums.KitchenTicketStatus;
import com.huisa.model.KitchenTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface  KitchenTicketRepository extends JpaRepository<KitchenTicket, Long> {

    Optional<KitchenTicket> findByOrderId(Long orderId);

    boolean existsByOrderId(Long orderId);

    List<KitchenTicket> findByStatus(KitchenTicketStatus status);
}
