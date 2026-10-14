package com.huisa.service.impl;

import com.huisa.dto.CategoryRequestDto;
import com.huisa.dto.CategoryResponseDto;
import com.huisa.model.Category;
import com.huisa.repository.CategoryRepository;
import com.huisa.service.CategoryService;
import com.huisa.util.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponseDto create (CategoryRequestDto request){
        Category category = categoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }

    @Override
    public CategoryResponseDto findById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));
        return categoryMapper.toDto(category);

    }

    @Override
    public List<CategoryResponseDto> findAll(){
       List<Category> categories = categoryRepository.findAll();
         return categoryMapper.toDtoList(categories);
    }
    @Override
    public CategoryResponseDto update(
            Long id,
            CategoryRequestDto request) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        categoryMapper.updateEntity(request, category);

        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toDto(updatedCategory);
    }

    @Override
    public void delete(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        categoryRepository.delete(category);
    }
}
