package com.example.pib2.service;

import com.example.pib2.model.product.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    ProductDto create(ProductDto dto);
    List<ProductDto> getAll();
    Optional<ProductDto> getById(Long id);
    List<ProductDto> getByCategory(Long categoryId);
    Optional<ProductDto> update(Long id, ProductDto dto);
    boolean delete(Long id);
}