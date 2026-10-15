package com.huisa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "delivery_addresses")
@NoArgsConstructor
public class DeliveryAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "country", nullable = false, length = 100)
    private String country;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "reference_note", columnDefinition = "TEXT")
    private String referenceNote;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;


}
