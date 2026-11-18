package com.huisa.repository;

import com.huisa.enums.KitchenItemStatus;
import com.huisa.model.TicketItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketItemRepository extends JpaRepository<TicketItem , Long> {

    List<TicketItem> findByTicketId(Long ticketId);

    List<TicketItem> findByStatus(KitchenItemStatus status);

}
