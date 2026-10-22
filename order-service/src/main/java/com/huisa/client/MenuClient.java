package com.huisa.client;

import com.huisa.dto.MenuItemResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "menu-service", path = "/menu-items")
public interface MenuClient {

    @GetMapping("/{id}")
    MenuItemResponseDto getMenuItemById(
            @PathVariable("id") Long id
    );
}
