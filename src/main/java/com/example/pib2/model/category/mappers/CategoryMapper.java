package com.example.pib2.model.category.mappers;

import com.example.pib2.model.category.dto.CategoryDto;
import com.example.pib2.model.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);
}