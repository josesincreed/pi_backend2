package com.example.pib2.service;

import com.example.pib2.model.purchaseItem.dto.PurchaseItemDto;

import java.util.List;
import java.util.Optional;

public interface PurchaseItemService {
    PurchaseItemDto create(PurchaseItemDto dto);
    List<PurchaseItemDto> getAll();
    Optional<PurchaseItemDto> getById(Long id);
    List<PurchaseItemDto> getByPurchaseId(Long purchaseId);
    Optional<PurchaseItemDto> update(Long id, PurchaseItemDto dto);
    boolean delete(Long id);
}