package com.huisa.dto;

import com.huisa.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import java.util.Set;

@Builder
public record UserRequestDto(


        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Password is required")
        String password,

        Boolean enabled,

        Set<Long> roleIds

) {}
