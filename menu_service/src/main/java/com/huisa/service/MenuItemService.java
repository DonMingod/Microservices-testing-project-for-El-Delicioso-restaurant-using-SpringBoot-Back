package com.huisa.service;

import java.util.List;
import com.huisa.dto.MenuItemRequestDto;


import com.huisa.dto.MenuItemResponseDto;

public interface MenuItemService {

    MenuItemResponseDto create(MenuItemRequestDto request);

    MenuItemResponseDto findById(Long id);

    List<MenuItemResponseDto> findAll();

    MenuItemResponseDto update(Long id, MenuItemRequestDto request);

    void delete(Long id);

}
