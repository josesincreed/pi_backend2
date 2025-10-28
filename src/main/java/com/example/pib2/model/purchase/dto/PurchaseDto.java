package com.example.pib2.model.purchase.dto;

import com.example.pib2.model.purchaseItem.dto.PurchaseItemDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDto {
    private Long id;
    private Long userId;
    private LocalDateTime purchaseDate;
    private Double totalAmount;
    private String city;
    private List<PurchaseItemDto> items; // Relación con los ítems
}
