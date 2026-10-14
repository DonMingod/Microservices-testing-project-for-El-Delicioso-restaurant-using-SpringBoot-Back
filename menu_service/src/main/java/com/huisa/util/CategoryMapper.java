package com.huisa.util;

import com.huisa.dto.CategoryRequestDto;
import com.huisa.dto.CategoryResponseDto;
import com.huisa.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper{

    Category toEntity(CategoryRequestDto request);

    CategoryResponseDto toDto(Category category);

    List<CategoryResponseDto> toDtoList(List<Category> categories);

    void updateEntity(
            CategoryRequestDto request,
            @MappingTarget Category category
    );
}
