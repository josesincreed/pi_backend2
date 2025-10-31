package com.example.pib2.service;

import com.example.pib2.model.purchaseItem.dto.PurchaseItemDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PurchaseItemService {

    PurchaseItemDto create(PurchaseItemDto dto);
    List<PurchaseItemDto> getAll();
    PurchaseItemDto getById(Long id); // lanza CustomException si no existe
    List<PurchaseItemDto> getByPurchaseId(Long purchaseId); // lanza CustomException si no hay ítems
    PurchaseItemDto update(Long id, PurchaseItemDto dto); // lanza CustomException si no existe
    void delete(Long id); // void y lanza CustomException si no existe
    void reduceStockAfterPurchase(List<PurchaseItemDto> purchasedItems);


}
