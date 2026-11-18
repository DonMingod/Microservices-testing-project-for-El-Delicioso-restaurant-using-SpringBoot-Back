package com.huisa.model;

import com.huisa.enums.KitchenItemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ticket_items")
public class TicketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_id", nullable = false)
    private KitchenTicket ticket;

    @Column(name = "menu_item_id", nullable = false)
    private Long menuItemId;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private KitchenItemStatus status;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "started_at")
    private LocalDateTime startedAt;;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;





}
