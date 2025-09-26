package com.example.pib2.controller;

import com.example.pib2.model.product.dto.ProductDto;
import com.example.pib2.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Crear producto
    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.create(dto));
    }

    // Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    // Obtener producto por id
    @GetMapping("/{id}")
    public ResponseEntity<Optional<ProductDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<Optional<ProductDto>> update(@PathVariable Long id, @RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.update(id, dto));
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(productService.delete(id));
    }
}