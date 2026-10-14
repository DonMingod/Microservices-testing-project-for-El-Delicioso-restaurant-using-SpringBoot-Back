package com.huisa.util;

import com.huisa.dto.RoleRequestDto;
import com.huisa.dto.RoleResponseDto;
import com.huisa.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Role toEntity(RoleRequestDto request);

    RoleResponseDto toDto(Role role);

    List<RoleResponseDto> toDtoList(List<Role> roles);
}
