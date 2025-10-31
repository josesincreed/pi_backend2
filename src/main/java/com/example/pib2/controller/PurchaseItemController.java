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

    @Operation(summary = "Crear ítem de compra", description = "Crea un nuevo ítem asociado a una compra existente.")
    @PostMapping
    public ResponseEntity<PurchaseItemDto> create(@RequestBody PurchaseItemDto dto) {
        return ResponseEntity.ok(purchaseItemService.create(dto));
    }

    @Operation(summary = "Obtener todos los ítems")
    @GetMapping
    public ResponseEntity<List<PurchaseItemDto>> getAll() {
        return ResponseEntity.ok(purchaseItemService.getAll());
    }

    @Operation(summary = "Obtener ítem por ID")
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseItemDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseItemService.getById(id));
    }

    @Operation(summary = "Obtener ítems por ID de compra")
    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<List<PurchaseItemDto>> getByPurchaseId(@PathVariable Long purchaseId) {
        return ResponseEntity.ok(purchaseItemService.getByPurchaseId(purchaseId));
    }

    @Operation(summary = "Actualizar ítem")
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseItemDto> update(@PathVariable Long id, @RequestBody PurchaseItemDto dto) {
        return ResponseEntity.ok(purchaseItemService.update(id, dto));
    }

    @Operation(summary = "Eliminar ítem")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        purchaseItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Reducir stock tras la compra",
            description = "Disminuye el stock de los productos comprados según la cantidad indicada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Error al procesar los productos")
    })
    @PatchMapping("/reduce-stock")
    public ResponseEntity<String> reduceStockAfterPurchase(@RequestBody List<PurchaseItemDto> purchasedItems) {
        try {
            purchaseItemService.reduceStockAfterPurchase(purchasedItems);
            return ResponseEntity.ok("Stock actualizado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}
