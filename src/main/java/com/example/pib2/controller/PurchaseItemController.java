package com.example.pib2.controller;

import com.example.pib2.model.purchaseItem.dto.PurchaseItemDto;
import com.example.pib2.service.PurchaseItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-items")
@RequiredArgsConstructor
@Tag(name = "Purchase Items", description = "Operaciones para la gestión de ítems dentro de una compra")
public class PurchaseItemController {

    private final PurchaseItemService purchaseItemService;

    // Crear ítem de compra
    @Operation(
            summary = "Crear ítem de compra",
            description = "Crea un nuevo ítem asociado a una compra existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ítem creado correctamente",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"id\": 1, \"purchaseId\": 101, \"productName\": \"Laptop Lenovo\", \"quantity\": 2, \"price\": 2500.00 }")
                    )),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<PurchaseItemDto> create(@RequestBody PurchaseItemDto dto) {
        return ResponseEntity.ok(purchaseItemService.create(dto));
    }

    // Obtener todos los ítems
    @Operation(
            summary = "Obtener todos los ítems",
            description = "Devuelve una lista con todos los ítems de compra registrados."
    )
    @ApiResponse(responseCode = "200", description = "Listado de ítems obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<PurchaseItemDto>> getAll() {
        return ResponseEntity.ok(purchaseItemService.getAll());
    }

    // Obtener ítem por ID
    @Operation(
            summary = "Obtener ítem por ID",
            description = "Busca y devuelve un ítem de compra según su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ítem encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"id\": 1, \"purchaseId\": 101, \"productName\": \"Laptop Lenovo\", \"quantity\": 2, \"price\": 2500.00 }")
                    )),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseItemDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseItemService.getById(id));
    }

    // Obtener ítems por ID de compra
    @Operation(
            summary = "Obtener ítems por ID de compra",
            description = "Devuelve todos los ítems asociados a una compra específica."
    )
    @ApiResponse(responseCode = "200", description = "Ítems obtenidos correctamente")
    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<List<PurchaseItemDto>> getByPurchaseId(@PathVariable Long purchaseId) {
        return ResponseEntity.ok(purchaseItemService.getByPurchaseId(purchaseId));
    }

    // Actualizar ítem
    @Operation(
            summary = "Actualizar ítem",
            description = "Actualiza los datos de un ítem de compra según su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ítem actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseItemDto> update(@PathVariable Long id, @RequestBody PurchaseItemDto dto) {
        return ResponseEntity.ok(purchaseItemService.update(id, dto));
    }

    // Eliminar ítem
    @Operation(
            summary = "Eliminar ítem",
            description = "Elimina un ítem de compra específico mediante su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ítem eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Ítem no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        purchaseItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
