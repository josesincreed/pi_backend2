package com.example.pib2.service;


import com.example.pib2.model.category.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    CategoryDto create(CategoryDto dto);
    List<CategoryDto> getAll();
    Optional<CategoryDto> getById(Long id);
    Optional<CategoryDto> update(Long id, CategoryDto dto);
    boolean delete(Long id);
}