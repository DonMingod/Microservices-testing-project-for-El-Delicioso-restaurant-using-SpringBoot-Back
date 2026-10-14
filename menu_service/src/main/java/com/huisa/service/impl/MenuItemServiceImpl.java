package com.huisa.service.impl;

import com.huisa.dto.MenuItemRequestDto;
import com.huisa.dto.MenuItemResponseDto;
import com.huisa.model.Category;
import com.huisa.model.MenuItem;
import com.huisa.repository.CategoryRepository;
import com.huisa.repository.MenuItemRepository;
import com.huisa.service.MenuItemService;
import com.huisa.util.MenuItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;
    private final MenuItemMapper menuItemMapper;

    @Override
    public MenuItemResponseDto create(MenuItemRequestDto request) {

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        MenuItem menuItem = menuItemMapper.toEntity(request);

        menuItem.setCategory(category);

        MenuItem savedMenuItem = menuItemRepository.save(menuItem);

        return menuItemMapper.toDto(savedMenuItem);
    }

    @Override
    public MenuItemResponseDto findById(Long id) {

        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Menu item not found"));

        return menuItemMapper.toDto(menuItem);
    }

    @Override
    public List<MenuItemResponseDto> findAll() {

        List<MenuItem> menuItems = menuItemRepository.findAll();

        return menuItemMapper.toDtoList(menuItems);
    }

    @Override
    public MenuItemResponseDto update(
            Long id,
            MenuItemRequestDto request) {

        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Menu item not found"));

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        menuItemMapper.updateEntity(request, menuItem);

        menuItem.setCategory(category);

        MenuItem updatedMenuItem = menuItemRepository.save(menuItem);

        return menuItemMapper.toDto(updatedMenuItem);
    }

    @Override
    public void delete(Long id) {

        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Menu item not found"));

        menuItemRepository.delete(menuItem);
    }

}
