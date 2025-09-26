package com.example.pib2.model.purchaseItem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItemDto {
    private Long id;
    private Integer quantity;
    private Double price;
    private Long purchaseId; // referencia a la compra
    private Long productId;  // referencia al producto
}