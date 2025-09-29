package com.example.pib2.service.implementation;

import com.example.pib2.exception.CustomException;
import com.example.pib2.helpers.TechnicalErrorMessage;
import com.example.pib2.model.product.dto.ProductDto;
import com.example.pib2.model.product.mappers.ProductMapper;
import com.example.pib2.model.product.Product;
import com.example.pib2.repository.ProductRepository;
import com.example.pib2.repository.CategoryRepository;
import com.example.pib2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto create(ProductDto dto) {
        if (!categoryRepository.existsById(dto.getCategoryId())) {
            throw new CustomException(
                    TechnicalErrorMessage.PRODUCT_CATEGORY_INVALID.getCode(),
                    TechnicalErrorMessage.PRODUCT_CATEGORY_INVALID.getMessage(dto.getCategoryId())
            );
        }

        Product product = productMapper.toEntity(dto);
        Product saved = productRepository.save(product);
        return productMapper.toDto(saved);
    }

    @Override
    public List<ProductDto> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        TechnicalErrorMessage.PRODUCT_NOT_FOUND.getCode(),
                        TechnicalErrorMessage.PRODUCT_NOT_FOUND.getMessage(id)
                ));
        return productMapper.toDto(product);
    }

    @Override
    public List<ProductDto> getByCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CustomException(
                    TechnicalErrorMessage.PRODUCT_CATEGORY_INVALID.getCode(),
                    TechnicalErrorMessage.PRODUCT_CATEGORY_INVALID.getMessage(categoryId)
            );
        }

        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto update(Long id, ProductDto dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new CustomException(
                        TechnicalErrorMessage.PRODUCT_NOT_FOUND.getCode(),
                        TechnicalErrorMessage.PRODUCT_NOT_FOUND.getMessage(id)
                ));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setStockQuantity(dto.getStockQuantity());
        existing.setImageUrl(dto.getImageUrl());

        if (!categoryRepository.existsById(dto.getCategoryId())) {
            throw new CustomException(
                    TechnicalErrorMessage.PRODUCT_CATEGORY_INVALID.getCode(),
                    TechnicalErrorMessage.PRODUCT_CATEGORY_INVALID.getMessage(dto.getCategoryId())
            );
        }

        existing.setCategory(productMapper.map(dto.getCategoryId()));

        return productMapper.toDto(productRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new CustomException(
                    TechnicalErrorMessage.PRODUCT_NOT_FOUND.getCode(),
                    TechnicalErrorMessage.PRODUCT_NOT_FOUND.getMessage(id)
            );
        }
        productRepository.deleteById(id);
    }
}
