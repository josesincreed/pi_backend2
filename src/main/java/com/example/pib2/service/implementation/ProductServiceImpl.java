package com.example.pib2.service.implementation;

import com.example.pib2.model.product.dto.ProductDto;
import com.example.pib2.model.product.mappers.ProductMapper;
import com.example.pib2.model.product.Product;
import com.example.pib2.repository.ProductRepository;
import com.example.pib2.repository.CategoryRepository;
import com.example.pib2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto create(ProductDto dto) {
        // Validar que la categoría exista
        if (!categoryRepository.existsById(dto.getCategoryId())) {
            throw new RuntimeException("La categoría con ID " + dto.getCategoryId() + " no existe");
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
    public Optional<ProductDto> getById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDto);
    }

    @Override
    public List<ProductDto> getByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> update(Long id, ProductDto dto) {
        return productRepository.findById(id)
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setDescription(dto.getDescription());
                    existing.setPrice(dto.getPrice());
                    existing.setStockQuantity(dto.getStockQuantity());
                    existing.setImageUrl(dto.getImageUrl());

                    // Validar categoría
                    if (!categoryRepository.existsById(dto.getCategoryId())) {
                        throw new RuntimeException("La categoría con ID " + dto.getCategoryId() + " no existe");
                    }
                    existing.setCategory(productMapper.map(dto.getCategoryId()));

                    return productMapper.toDto(productRepository.save(existing));
                });
    }

    @Override
    public boolean delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}