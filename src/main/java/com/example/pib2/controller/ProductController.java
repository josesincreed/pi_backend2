package com.example.pib2.controller;

import com.example.pib2.model.product.dto.ProductDto;
import com.example.pib2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Crear producto
    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto) {
        ProductDto created = productService.create(dto);
        return ResponseEntity.ok(created);
    }

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    // Obtener producto por id
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        ProductDto product = productService.getById(id);
        return ResponseEntity.ok(product);
    }

    // Obtener productos por categoría
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDto>> getByCategory(@PathVariable Long categoryId) {
        List<ProductDto> products = productService.getByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody ProductDto dto) {
        ProductDto updated = productService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
