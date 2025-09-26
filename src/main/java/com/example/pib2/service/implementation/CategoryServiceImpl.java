package com.example.pib2.service.implementation;

import com.example.pib2.exception.CustomException;
import com.example.pib2.helpers.TechnicalErrorMessage;
import com.example.pib2.model.category.dto.CategoryDto;
import com.example.pib2.model.category.mappers.CategoryMapper;
import com.example.pib2.model.category.Category;
import com.example.pib2.repository.CategoryRepository;
import com.example.pib2.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    // Constructor explícito para inyección de dependencias
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto create(CategoryDto dto) {
        Category category = categoryMapper.toEntity(dto);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new CustomException(
                        TechnicalErrorMessage.CATEGORY_NOT_FOUND.getCode(),
                        TechnicalErrorMessage.CATEGORY_NOT_FOUND.getMessage(id)
                ));
    }

    @Override
    public CategoryDto update(Long id, CategoryDto dto) {
        return categoryRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setDescription(dto.getDescription());
                    Category updated = categoryRepository.save(existing);
                    return categoryMapper.toDto(updated);
                })
                .orElseThrow(() -> new CustomException(
                        TechnicalErrorMessage.CATEGORY_NOT_FOUND.getCode(),
                        TechnicalErrorMessage.CATEGORY_NOT_FOUND.getMessage(id)
                ));
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CustomException(
                    TechnicalErrorMessage.CATEGORY_NOT_FOUND.getCode(),
                    TechnicalErrorMessage.CATEGORY_NOT_FOUND.getMessage(id)
            );
        }
        categoryRepository.deleteById(id);
    }
}
