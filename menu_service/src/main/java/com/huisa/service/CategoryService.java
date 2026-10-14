package com.huisa.service;

import com.huisa.dto.CategoryRequestDto;
import com.huisa.dto.CategoryResponseDto;
import java.util.List;

public interface CategoryService {


    CategoryResponseDto create (CategoryRequestDto request);

    CategoryResponseDto findById (Long id);

    List<CategoryResponseDto> findAll ();

    CategoryResponseDto update (Long id, CategoryRequestDto request);

    void delete(Long id);
}
