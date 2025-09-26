package com.example.pib2.controller;

import com.example.pib2.model.category.dto.CategoryDto;
import com.example.pib2.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    // Crear categoría
    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto dto) {
        CategoryDto created = categoryService.create(dto);
        return ResponseEntity.ok(created);
    }

    // Obtener todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        List<CategoryDto> categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    // Obtener categoría por id
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
        CategoryDto category = categoryService.getById(id);
        return ResponseEntity.ok(category);
    }

    // Actualizar categoría
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody CategoryDto dto) {
        CategoryDto updated = categoryService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Eliminar categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
