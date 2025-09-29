package com.example.pib2.controller;

import com.example.pib2.model.purchaseItem.dto.PurchaseItemDto;
import com.example.pib2.service.PurchaseItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-items")
@RequiredArgsConstructor
public class PurchaseItemController {

    private final PurchaseItemService purchaseItemService;

    // Crear ítem de compra
    @PostMapping
    public ResponseEntity<PurchaseItemDto> create(@RequestBody PurchaseItemDto dto) {
        return ResponseEntity.ok(purchaseItemService.create(dto));
    }

    // Obtener todos los ítems
    @GetMapping
    public ResponseEntity<List<PurchaseItemDto>> getAll() {
        return ResponseEntity.ok(purchaseItemService.getAll());
    }

    // Obtener ítem por ID
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseItemDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseItemService.getById(id));
    }

    // Obtener ítems por ID de compra
    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<List<PurchaseItemDto>> getByPurchaseId(@PathVariable Long purchaseId) {
        return ResponseEntity.ok(purchaseItemService.getByPurchaseId(purchaseId));
    }

    // Actualizar ítem
    @PutMapping("/{id}")
    public ResponseEntity<PurchaseItemDto> update(@PathVariable Long id, @RequestBody PurchaseItemDto dto) {
        return ResponseEntity.ok(purchaseItemService.update(id, dto));
    }

    // Eliminar ítem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        purchaseItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
