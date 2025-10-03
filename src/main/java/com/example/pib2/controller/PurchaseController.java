package com.example.pib2.controller;

import com.example.pib2.model.purchase.dto.PurchaseDto;
import com.example.pib2.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
@Tag(name = "Purchases", description = "Operaciones relacionadas con las compras y sus ítems")
public class PurchaseController {

    private final PurchaseService purchaseService;

    @Operation(
            summary = "Crear una nueva compra",
            description = "Crea una compra con sus ítems asociados a partir de un objeto PurchaseDto."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Compra creada exitosamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PurchaseDto.class))),
            @ApiResponse(responseCode = "400", description = "Datos de la compra inválidos", content = @Content)
    })
    @PostMapping
    public ResponseEntity<PurchaseDto> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto PurchaseDto con los datos de la compra e ítems",
                    required = true,
                    content = @Content(schema = @Schema(implementation = PurchaseDto.class)))
            @RequestBody PurchaseDto dto) {
        return ResponseEntity.ok(purchaseService.create(dto));
    }

    @Operation(
            summary = "Obtener todas las compras",
            description = "Devuelve una lista con todas las compras registradas en el sistema."
    )
    @GetMapping
    public ResponseEntity<List<PurchaseDto>> getAll() {
        return ResponseEntity.ok(purchaseService.getAll());
    }

    @Operation(
            summary = "Obtener compras por usuario",
            description = "Devuelve todas las compras realizadas por un usuario específico."
    )
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PurchaseDto>> getByUserId(
            @Parameter(description = "ID del usuario", example = "1") @PathVariable Long userId) {
        return ResponseEntity.ok(purchaseService.getByUserId(userId));
    }

    @Operation(
            summary = "Obtener una compra por ID",
            description = "Devuelve los detalles de una compra específica según su ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDto> getById(
            @Parameter(description = "ID de la compra", example = "10") @PathVariable Long id) {
        return ResponseEntity.ok(purchaseService.getById(id));
    }

    @Operation(
            summary = "Eliminar una compra",
            description = "Elimina una compra existente según su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Compra eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Compra no encontrada", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID de la compra a eliminar", example = "5") @PathVariable Long id) {
        purchaseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
