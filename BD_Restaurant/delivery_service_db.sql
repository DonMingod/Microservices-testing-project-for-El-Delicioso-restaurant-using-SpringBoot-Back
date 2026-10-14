CREATE DATABASE IF NOT EXISTS restaurant_delivery_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE restaurant_delivery_db;

CREATE TABLE IF NOT EXISTS delivery_personnel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL CHECK (phone REGEXP '^[0-9+\\-() ]{7,}$'),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS deliveries (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL UNIQUE,
    personnel_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'ASSIGNED',
    picked_up_at TIMESTAMP NULL,
    delivered_at TIMESTAMP NULL,
    current_latitude DECIMAL(10, 8),
    current_longitude DECIMAL(11, 8),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (personnel_id) REFERENCES delivery_personnel(id),
    INDEX idx_status (status),
    INDEX idx_order_id (order_id),
    INDEX idx_personnel_id (personnel_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS delivery_assignments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    delivery_id BIGINT NOT NULL UNIQUE,
    personnel_id BIGINT NOT NULL,
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP NULL,
    FOREIGN KEY (delivery_id) REFERENCES deliveries(id) ON DELETE CASCADE,
    FOREIGN KEY (personnel_id) REFERENCES delivery_personnel(id) ON DELETE CASCADE,
    INDEX idx_personnel_id (personnel_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;