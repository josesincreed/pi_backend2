package com.example.pib2.service;

import com.example.pib2.model.product.dto.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto create(ProductDto dto);
    List<ProductDto> getAll();
    ProductDto getById(Long id);
    List<ProductDto> getByCategory(Long categoryId);
    ProductDto update(Long id, ProductDto dto);
    void delete(Long id);
}
