package com.example.pib2.model.category.mappers;

import com.example.pib2.model.category.dto.CategoryDto;
import com.example.pib2.model.category.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);
}
