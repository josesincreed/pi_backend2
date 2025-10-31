package com.example.pib2.model.category.mappers;

import com.example.pib2.model.category.dto.CategoryDto;
import com.example.pib2.model.category.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "imageUrl", source = "imageUrl")
    CategoryDto toDto(Category category);

    @Mapping(target = "imageUrl", source = "imageUrl")
    Category toEntity(CategoryDto categoryDto);
}
