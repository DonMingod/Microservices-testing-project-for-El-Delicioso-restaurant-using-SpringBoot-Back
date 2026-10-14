package com.huisa.util;

import com.huisa.dto.MenuItemRequestDto;
import com.huisa.dto.MenuItemResponseDto;
import com.huisa.model.MenuItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface MenuItemMapper {

    @Mapping(target = "category", ignore = true)
    MenuItem toEntity(MenuItemRequestDto request);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    MenuItemResponseDto toDto(MenuItem menuItem);

    List<MenuItemResponseDto> toDtoList(List<MenuItem> menuItems);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(
            MenuItemRequestDto request,
            @MappingTarget MenuItem menuItem
    );
}
