package com.example.pib2.controller;

import com.example.pib2.model.category.dto.CategoryDto;
import com.example.pib2.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categories", description = "Operaciones relacionadas con las categorías de productos")
public class CategoryController {

    private final CategoryService categoryService;

    // Crear categoría (ADMIN)
    @Operation(
            summary = "Crear categoría",
            description = "Permite a un administrador crear una nueva categoría.",
            security = { @SecurityRequirement(name = "basicAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría creada correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"id\": 1, \"name\": \"Electrónica\" }")
                    )),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - se requiere rol ADMIN")
    })
    @PostMapping
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto dto) {
        CategoryDto created = categoryService.create(dto);
        return ResponseEntity.ok(created);
    }

    // Obtener todas las categorías (Público)
    @Operation(
            summary = "Obtener todas las categorías",
            description = "Devuelve el listado completo de categorías disponibles."
    )
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        List<CategoryDto> categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    // Obtener categoría por id (Público)
    @Operation(
            summary = "Obtener categoría por ID",
            description = "Devuelve la información de una categoría específica según su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"id\": 2, \"name\": \"Hogar\" }")
                    )),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
        CategoryDto category = categoryService.getById(id);
        return ResponseEntity.ok(category);
    }

    // Actualizar categoría (ADMIN)
    @Operation(
            summary = "Actualizar categoría",
            description = "Permite a un administrador actualizar los datos de una categoría existente.",
            security = { @SecurityRequirement(name = "basicAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoría actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "401", description = "No autorizado - se requiere rol ADMIN")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody CategoryDto dto) {
        CategoryDto updated = categoryService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Eliminar categoría (ADMIN)
    @Operation(
            summary = "Eliminar categoría",
            description = "Permite a un administrador eliminar una categoría.",
            security = { @SecurityRequirement(name = "basicAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoría eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Categoría no encontrada"),
            @ApiResponse(responseCode = "401", description = "No autorizado - se requiere rol ADMIN")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
