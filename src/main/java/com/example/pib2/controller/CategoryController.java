package com.example.pib2.controller;

import com.example.pib2.model.category.dto.CategoryDto;
import com.example.pib2.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Crear categoría
    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    // Obtener categoría por id
    @GetMapping("/{id}")
    public ResponseEntity<Optional<CategoryDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<Optional<CategoryDto>> update(@PathVariable Long id, @RequestBody CategoryDto dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    // Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }
}