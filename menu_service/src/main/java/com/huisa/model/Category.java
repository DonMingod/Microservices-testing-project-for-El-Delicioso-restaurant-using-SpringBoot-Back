package com.huisa.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")
public class Category {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @Column (nullable = false, unique = true, length = 100)
     private String name;

     @Column
     private String description;

     @Column(name = "is_active", nullable = false)
     private Boolean isActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
        if (this.isActive == null) {
            this.isActive = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
