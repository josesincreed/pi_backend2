package com.example.pib2.controller;

import com.example.pib2.model.product_details.dto.ProductDetailDTO;
import com.example.pib2.service.ProductDetailService;
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
@RequestMapping("/api/product-details")
@RequiredArgsConstructor
@Tag(name = "Product Details", description = "Operaciones relacionadas con los detalles de los productos")
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    // Crear detalle de producto (ADMIN)
    @Operation(
            summary = "Crear detalle de producto",
            description = "Permite a un administrador crear un nuevo detalle de producto.",
            security = { @SecurityRequirement(name = "basicAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle de producto creado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"productId\": 1, \"origen\": \"Colombia\", \"tipoArtesania\": \"Tejido\", \"oficio\": \"Tejeduría\", \"materiaPrima\": \"Algodón\", \"etnia\": \"Wayuu\", \"programa\": \"Artesanías Locales\", \"sku\": \"SKU001\", \"descripcionDetallada\": \"Detalle completo del producto\" }")
                    )),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "401", description = "No autorizado - se requiere rol ADMIN")
    })
    @PostMapping
    public ResponseEntity<ProductDetailDTO> create(@RequestBody ProductDetailDTO dto) {
        ProductDetailDTO created = productDetailService.createProduct(dto);
        return ResponseEntity.ok(created);
    }

    // Obtener todos los detalles de productos (Público)
    @Operation(
            summary = "Obtener todos los detalles de productos",
            description = "Devuelve el listado completo de detalles de productos disponibles."
    )
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<ProductDetailDTO>> getAll() {
        return ResponseEntity.ok(productDetailService.getAllProducts());
    }

    // Obtener detalle de producto por ID (Público)
    @Operation(
            summary = "Obtener detalle de producto por ID",
            description = "Busca y devuelve un detalle de producto según su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"productId\": 1, \"origen\": \"Colombia\", \"tipoArtesania\": \"Tejido\", \"oficio\": \"Tejeduría\", \"materiaPrima\": \"Algodón\", \"etnia\": \"Wayuu\", \"programa\": \"Artesanías Locales\", \"sku\": \"SKU001\", \"descripcionDetallada\": \"Detalle completo del producto\" }")
                    )),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> getById(@PathVariable Long id) {
        ProductDetailDTO detail = productDetailService.getProductById(id);
        return ResponseEntity.ok(detail);
    }

    // Actualizar detalle de producto (ADMIN)
    @Operation(
            summary = "Actualizar detalle de producto",
            description = "Permite a un administrador actualizar los datos de un detalle de producto existente.",
            security = { @SecurityRequirement(name = "basicAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado - se requiere rol ADMIN")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDetailDTO> update(@PathVariable Long id, @RequestBody ProductDetailDTO dto) {
        ProductDetailDTO updated = productDetailService.updateProduct(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Eliminar detalle de producto (ADMIN)
    @Operation(
            summary = "Eliminar detalle de producto",
            description = "Permite a un administrador eliminar un detalle de producto.",
            security = { @SecurityRequirement(name = "basicAuth") }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Detalle eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
            @ApiResponse(responseCode = "401", description = "No autorizado - se requiere rol ADMIN")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productDetailService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
