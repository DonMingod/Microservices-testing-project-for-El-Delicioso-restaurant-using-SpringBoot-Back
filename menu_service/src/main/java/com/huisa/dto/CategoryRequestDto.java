package com.huisa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryRequestDto(

        @NotBlank
        @Size(max = 100)
        String name,

        String description,

        Boolean active
) {
}
