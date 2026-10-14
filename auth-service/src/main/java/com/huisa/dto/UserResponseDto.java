package com.huisa.dto;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record UserResponseDto(

        Long id,

        String email,

        String name,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        LocalDateTime lastLogin,

        Boolean enabled,

        Set<RoleResponseDto> roles
) {}
