-- Create media_db if not exists
CREATE DATABASE IF NOT EXISTS restaurant_media_db
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE restaurant_media_db;

-- Media table (solo guardamos nombre de archivo + metadata)
CREATE TABLE IF NOT EXISTS media (
    id VARCHAR(36) PRIMARY KEY,
    owner_type VARCHAR(50) NOT NULL,
    owner_id BIGINT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(100) NOT NULL,
    file_size BIGINT NOT NULL,
    is_primary BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_owner (owner_type, owner_id),
    INDEX idx_primary (is_primary),
    UNIQUE KEY uk_owner_primary (owner_type, owner_id, is_primary)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
