package com.example.pib2.controller;


import com.example.pib2.model.purchase.dto.PurchaseDto;
import com.example.pib2.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    // Crear compra con ítems
    @PostMapping
    public ResponseEntity<PurchaseDto> create(@RequestBody PurchaseDto dto) {
        return ResponseEntity.ok(purchaseService.create(dto));
    }

    // Obtener todas las compras
    @GetMapping
    public ResponseEntity<List<PurchaseDto>> getAll() {
        return ResponseEntity.ok(purchaseService.getAll());
    }

    // Obtener compras por usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PurchaseDto>> getByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(purchaseService.getByUserId(userId));
    }

    // Obtener compra por ID
    @GetMapping("/{id}")
    public ResponseEntity<Optional<PurchaseDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.getById(id));
    }

    // Eliminar compra
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.delete(id));
    }
}