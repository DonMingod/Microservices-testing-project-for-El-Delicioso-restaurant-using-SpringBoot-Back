package com.huisa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.NonNull;

@Builder
public record RoleRequestDto(

        @NotBlank(message = "Role name is required")
        String name,

        String description

) {}
