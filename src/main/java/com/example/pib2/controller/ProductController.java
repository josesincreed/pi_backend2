
        package com.example.pib2.controller;

        import com.example.pib2.model.product.dto.ProductDto;
        import com.example.pib2.service.ProductService;
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
        @RequestMapping("/api/products")
        @RequiredArgsConstructor
        @Tag(name = "Products", description = "Operaciones relacionadas con los productos de la tienda")
        public class ProductController {

        private final ProductService productService;

        // Crear producto (ADMIN)
        @Operation(
        summary = "Crear producto",
        description = "Permite a un administrador crear un nuevo producto en la tienda.",
        security = { @SecurityRequirement(name = "basicAuth") }
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto creado correctamente",
        content = @Content(
        mediaType = "application/json",
        examples = @ExampleObject(value = "{ \"id\": 1, \"name\": \"Teclado Mecánico\", \"price\": 120.0, \"stock\": 15, \"categoryId\": 2 }")
        )),
        @ApiResponse(responseCode = "400", description = "Datos inválidos"),
        @ApiResponse(responseCode = "401", description = "No autorizado - se requiere rol ADMIN")
        })
        @PostMapping
        public ResponseEntity<ProductDto> create(@RequestBody ProductDto dto) {
        ProductDto created = productService.create(dto);
        return ResponseEntity.ok(created);
        }

        // Obtener todos los productos (Público)
        @Operation(
        summary = "Obtener todos los productos",
        description = "Devuelve el listado completo de productos disponibles en la tienda."
        )
        @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
        @GetMapping
        public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(productService.getAll());
        }

        // Obtener producto por id (Público)
        @Operation(
        summary = "Obtener producto por ID",
        description = "Busca y devuelve un producto específico según su identificador único."
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado",
        content = @Content(
        mediaType = "application/json",
        examples = @ExampleObject(value = "{ \"id\": 1, \"name\": \"Teclado Mecánico\", \"price\": 120.0, \"stock\": 15, \"categoryId\": 2 }")
        )),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
        })
        @GetMapping("/{id}")
        public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        ProductDto product = productService.getById(id);
        return ResponseEntity.ok(product);
        }

        // Obtener productos por categoría (Público)
        @Operation(
        summary = "Obtener productos por categoría",
        description = "Devuelve todos los productos que pertenecen a una categoría específica."
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Listado de productos por categoría obtenido",
        content = @Content(
        mediaType = "application/json",
        examples = @ExampleObject(value = "[{ \"id\": 3, \"name\": \"Mouse Gamer\", \"price\": 45.0, \"stock\": 30, \"categoryId\": 2 }]")
        )),
        @ApiResponse(responseCode = "404", description = "Categoría no encontrada")
        })
        @GetMapping("/category/{categoryId}")
        public ResponseEntity<List<ProductDto>> getByCategory(@PathVariable Long categoryId) {
        List<ProductDto> products = productService.getByCategory(categoryId);
        return ResponseEntity.ok(products);
        }

        // Actualizar producto (ADMIN)
        @Operation(
        summary = "Actualizar producto",
        description = "Permite a un administrador actualizar los datos de un producto existente.",
        security = { @SecurityRequirement(name = "basicAuth") }
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "401", description = "No autorizado - se requiere rol ADMIN")
        })
        @PutMapping("/{id}")
        public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody ProductDto dto) {
        ProductDto updated = productService.update(id, dto);
        return ResponseEntity.ok(updated);
        }

        // Eliminar producto (ADMIN)
        @Operation(
        summary = "Eliminar producto",
        description = "Permite a un administrador eliminar un producto de la tienda.",
        security = { @SecurityRequirement(name = "basicAuth") }
        )
        @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
        @ApiResponse(responseCode = "401", description = "No autorizado - se requiere rol ADMIN")
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
        }
        }
